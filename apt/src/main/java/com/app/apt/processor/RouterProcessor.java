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
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
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
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ 实例化工厂 此类由apt自动生成");
        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("go")
                .addJavadoc("@此方法由apt自动生成")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(String.class, "name").addParameter(HashMap.class, "extra")
                .addParameter(ClassName.get("android.view", "View"), "view");
        MethodSpec.Builder methodBuilder2 = MethodSpec.methodBuilder("autoValue")
                .addJavadoc("@此方法由apt自动生成")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(ClassName.get("android.app", "Activity"), "mContext");

        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilder1 = CodeBlock.builder();
        CodeBlock.Builder blockBuilder2 = CodeBlock.builder();
        ClassName appClassName = ClassName.get("com", "App");
        blockBuilder1.addStatement("$T.getAppContext().mCurActivityExtra=extra", appClassName);
        blockBuilder1.addStatement("Activity mContext=$T.getAppContext().getCurActivity()", appClassName);
        blockBuilder1.beginControlFlow(" switch (name)");//括号开始
        blockBuilder2.beginControlFlow(" switch (mContext.getClass().getSimpleName())");//括号开始

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
                mRouterActivityModels.add(mRouterActivityModel);
            }
            ClassName mActivityCompatName = ClassName.get("android.support.v4.app", "ActivityCompat");
            ClassName mIntentClassName = ClassName.get("android.content", "Intent");
            ClassName mActivityOptionsCompatName = ClassName.get("android.support.v4.app", "ActivityOptionsCompat");
            for (RouterActivityModel item : mRouterActivityModels) {
                blockBuilder1.add("case $S: \n", item.getActionName());//1
                blockBuilder2.add("case $S: \n", item.getElement().getSimpleName());//1
                if (item.getExtraElements() != null && item.getExtraElements().size() > 0) {
                    for (int i = 0; i < item.getExtraElements().size(); i++) {
                        Element mFiled = item.getExtraElements().get(i);
                        blockBuilder2.add("(($T)mContext)." +
                                        "$L" +
                                        "= ($T) " +
                                        "$T.getAppContext()" +
                                        ".mCurActivityExtra.get(" +
                                        "$S);\n",
                                item.getElement(),
                                mFiled,
                                mFiled,
                                appClassName,
                                item.getExtraElementKeys().get(i)
                        );//5
                    }
                }
                if (item.getSceneTransitionElement() != null) {
                    blockBuilder1.add("$L.startActivity(mContext," +//2
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

                    blockBuilder2.add(
                            "$T.setTransitionName(" +//2
                                    "(($T)mContext)." +//3
                                    "$L, " +//4
                                    "$S);\n",//5
                            ClassName.get("android.support.v4.view", "ViewCompat"),//2
                            item.getElement(),//3
                            item.getSceneTransitionElement(),//4
                            item.getSceneTransitionElementName());//5
                } else {
                    blockBuilder1.add("mContext.startActivity(" +//2
                                    "\nnew $L(mContext," +//3
                                    "\n$L.class));", //7
                            mIntentClassName,//3
                            item.getElement()//4
                    );
                }
                blockBuilder1.addStatement("break");//1
                blockBuilder2.addStatement("break");//1
            }
            blockBuilder1.addStatement("default: break");
            blockBuilder1.endControlFlow();
            methodBuilder1.addCode(blockBuilder1.build());
            blockBuilder2.addStatement("default: break");
            blockBuilder2.endControlFlow();
            methodBuilder2.addCode(blockBuilder2.build());

            tb.addMethod(methodBuilder1.build());
            tb.addMethod(methodBuilder2.build());
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
