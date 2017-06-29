package com.app.apt.processor;

import com.app.annotation.apt.Extra;
import com.app.annotation.apt.Router;
import com.app.annotation.apt.SceneTransition;
import com.app.apt.AnnotationProcessor;
import com.app.apt.helper.RouterActivityModel;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/12/30.
 */

public class RouterProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        String CLASS_NAME = "TRouter";
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ 全局路由器 此类由apt自动生成");

        FieldSpec extraField = FieldSpec.builder(ParameterizedTypeName.get(HashMap.class, String.class, Object.class), "mCurActivityExtra")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .build();
        tb.addField(extraField);

        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("go")
                .addJavadoc("@此方法由apt自动生成")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(String.class, "name").addParameter(HashMap.class, "extra")
                .addParameter(ClassName.get("android.view", "View"), "view");

        MethodSpec.Builder methodBuilder2 = MethodSpec.methodBuilder("bind")
                .addJavadoc("@此方法由apt自动生成")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(ClassName.get("android.app", "Activity"), "mContext");

        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilderGo = CodeBlock.builder();
        CodeBlock.Builder blockBuilderBind = CodeBlock.builder();
        ClassName appClassName = ClassName.get("com", "App");
        blockBuilderGo.addStatement("mCurActivityExtra=extra");
        blockBuilderGo.addStatement("Activity mContext=$T.getAppContext().getCurActivity()", appClassName);
        blockBuilderGo.beginControlFlow(" switch (name)");//括号开始
        blockBuilderBind.addStatement("if(mCurActivityExtra==null) return");
        blockBuilderBind.beginControlFlow(" switch (mContext.getClass().getSimpleName())");//括号开始

        List<RouterActivityModel> mRouterActivityModels = new ArrayList<>();
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Router.class))) {
                ClassName currentType = ClassName.get(element);
                if (mList.contains(currentType)) continue;
                mList.add(currentType);
                RouterActivityModel mRouterActivityModel = new RouterActivityModel();
                mRouterActivityModel.setElement(element);
                mRouterActivityModel.setActionName(element.getAnnotation(Router.class).value());
                List<Element> mExtraElements = new ArrayList<>();
                List<String> mExtraElementKeys = new ArrayList<>();
                for (Element childElement : element.getEnclosedElements()) {
                    SceneTransition mSceneTransitionAnnotation = childElement.getAnnotation(SceneTransition.class);
                    if (mSceneTransitionAnnotation != null) {
                        mRouterActivityModel.setSceneTransitionElementName(mSceneTransitionAnnotation.value());
                        mRouterActivityModel.setSceneTransitionElement(childElement);
                    }
                    Extra mExtraAnnotation = childElement.getAnnotation(Extra.class);
                    if (mExtraAnnotation != null) {
                        mExtraElementKeys.add(mExtraAnnotation.value());
                        mExtraElements.add(childElement);
                    }
                }
                mRouterActivityModel.setExtraElementKeys(mExtraElementKeys);
                mRouterActivityModel.setExtraElements(mExtraElements);
                boolean isNeedBind = (mExtraElementKeys != null && mExtraElementKeys.size() > 0
                        || mRouterActivityModel.getSceneTransitionElement() != null);
                mRouterActivityModel.setNeedBind(isNeedBind);
                mRouterActivityModels.add(mRouterActivityModel);
            }
            ClassName mActivityCompatName = ClassName.get("android.support.v4.app", "ActivityCompat");
            ClassName mIntentClassName = ClassName.get("android.content", "Intent");
            ClassName mActivityOptionsCompatName = ClassName.get("android.support.v4.app", "ActivityOptionsCompat");
            for (RouterActivityModel item : mRouterActivityModels) {
                blockBuilderGo.add("case $S: \n", item.getActionName());//1
                if (item.isNeedBind())
                    blockBuilderBind.add("case $S: \n", item.getElement().getSimpleName());//1
                if (item.getExtraElements() != null && item.getExtraElements().size() > 0) {
                    for (int i = 0; i < item.getExtraElements().size(); i++) {
                        Element mFiled = item.getExtraElements().get(i);
                        blockBuilderBind.add("(($T)mContext)." +//1
                                        "$L" +//2
                                        "= ($T) " +//3
                                        "mCurActivityExtra.get(" +//4
                                        "$S);\n",//5
                                item.getElement(),//1
                                mFiled,//2
                                mFiled,//3
                                item.getExtraElementKeys().get(i)//5
                        );//5
                    }
                }
                if (item.getSceneTransitionElement() != null) {
                    blockBuilderGo.add("$L.startActivity(mContext," +//2
                                    "\nnew $L(mContext," +//3
                                    "\n$L.class)," +//4
                                    "\n$T.makeSceneTransitionAnimation(" +//5
                                    "\nmContext,view," +//6
                                    "\n$S).toBundle());", //7
                            mActivityCompatName,//2
                            mIntentClassName,//3
                            item.getElement(),//4
                            mActivityOptionsCompatName,//5
                            item.getSceneTransitionElementName());//6

                    blockBuilderBind.add(
                            "$T.setTransitionName(" +//2
                                    "(($T)mContext).mViewBinding." +//3
                                    "$L, " +//4
                                    "$S);\n",//5
                            ClassName.get("android.support.v4.view", "ViewCompat"),//2
                            item.getElement(),//3
                            item.getSceneTransitionElement(),//4
                            item.getSceneTransitionElementName());//5
                } else {
                    blockBuilderGo.add("mContext.startActivity(" +//2
                                    "\nnew $L(mContext," +//3
                                    "\n$L.class));", //7
                            mIntentClassName,//3
                            item.getElement()//4
                    );
                }
                blockBuilderGo.addStatement("\nbreak");//1
                if (item.isNeedBind()) blockBuilderBind.addStatement("break");//1
            }
            blockBuilderGo.addStatement("default: break");
            blockBuilderGo.endControlFlow();
            methodBuilder1.addCode(blockBuilderGo.build());
            blockBuilderBind.addStatement("default: break");
            blockBuilderBind.endControlFlow();
            methodBuilder2.addCode(blockBuilderBind.build());

            tb.addMethod(methodBuilder1.build());
            tb.addMethod(methodBuilder2.build());

            //增加go(action)和go(action,extra):两个重载方法
            tb.addMethod(MethodSpec.methodBuilder("go")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC, STATIC)
                    .addParameter(String.class, "name")
                    .addParameter(HashMap.class, "extra")
                    .addCode("go(name,extra,null);\n").build());

            tb.addMethod(MethodSpec.methodBuilder("go")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC, STATIC)
                    .addParameter(String.class, "name")
                    .addCode("go(name,null,null);\n").build());

            JavaFile javaFile = JavaFile.builder(Utils.PackageName, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
