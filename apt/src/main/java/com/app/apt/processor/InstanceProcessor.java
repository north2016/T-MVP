package com.app.apt.processor;

import com.app.annotation.apt.Instance;
import com.app.annotation.aspect.MemoryCache;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.NoPackageNameException;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/10/8.
 * 实例化工厂注解处理器
 */

public class InstanceProcessor implements IProcessor {
    //逻辑很简单,这里直接生成代码,就没有封装成面向对象的方式
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        TypeElement mElement = null;
        String CLASS_NAME = "InstanceFactory"; // 设置你要生成的代码class名字
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ 实例化工厂 此类由apt自动生成");
        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("create").addAnnotation(MemoryCache.class)
                .addJavadoc("@此方法由apt自动生成")
                .returns(Object.class).addModifiers(PUBLIC, STATIC).addException(IllegalAccessException.class).addException(InstantiationException.class)
                .addParameter(Class.class, "mClass").addParameter(ClassName.get("android.view", "View"), "view");
        ;

        MethodSpec.Builder methodBuilder2 = MethodSpec.methodBuilder("createVH")
                .addJavadoc("@此方法由apt自动生成")
                .returns(Object.class).addModifiers(PUBLIC, STATIC)
                .addParameter(Class.class, "mClass").addParameter(ClassName.get("android.view", "View"), "view");


        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilder1 = CodeBlock.builder();
        CodeBlock.Builder blockBuilder2 = CodeBlock.builder();
        blockBuilder1.beginControlFlow(" switch (mClass.getSimpleName())");//括号开始
        blockBuilder2.beginControlFlow(" switch (mClass.getSimpleName())");//括号开始
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Instance.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                if (mElement == null) mElement = element;
                if (!Utils.isValidClass(mAbstractProcessor.mMessager, element)) return;
                ClassName currentType = ClassName.get(element);
                if (mList.contains(currentType)) continue;
                mList.add(currentType);
                if (element.getAnnotation(Instance.class).type() == Instance.typeDefault)
                    blockBuilder1.addStatement("case $S: return  new $T()", currentType.simpleName(), currentType);
                else if (element.getAnnotation(Instance.class).type() == Instance.typeVH) {
                    blockBuilder1.addStatement("case $S: return  new $T(view)", currentType.simpleName(), currentType);
                    blockBuilder2.addStatement("case $S: return new $T(view)", currentType.simpleName(), currentType);
                }
            }
            blockBuilder1.addStatement("default: return mClass.newInstance()");
            blockBuilder1.endControlFlow();
            methodBuilder1.addCode(blockBuilder1.build());
            blockBuilder2.addStatement("default: return null");
            blockBuilder2.endControlFlow();
            methodBuilder2.addCode(blockBuilder2.build());

            tb.addMethod(methodBuilder1.build());
            tb.addMethod(methodBuilder2.build());
            if (mElement == null) {
                // mMessager.printMessage(Diagnostic.Kind.ERROR, "apt处理失败!");
                return;
            }
            String packageName = Utils.getPackageName(mAbstractProcessor.mElements, mElement);
            JavaFile javaFile = JavaFile.builder(packageName, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
            // javaFile.writeTo(new File(System.getProperty("user.home") + "/Desktop/")); // 测试在桌面生成一份源代码,方便查看
        } catch (NoPackageNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
