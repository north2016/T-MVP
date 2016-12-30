package com.app.apt.processor;

import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.aspect.MemoryCache;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
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
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        String CLASS_NAME = "InstanceFactory";
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ 实例化工厂 此类由apt自动生成");
        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("create").addAnnotation(MemoryCache.class)
                .addJavadoc("@此方法由apt自动生成")
                .returns(Object.class).addModifiers(PUBLIC, STATIC).addException(IllegalAccessException.class).addException(InstantiationException.class)
                .addParameter(Class.class, "mClass").addParameter(ClassName.get("android.view", "View"), "view");

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
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(InstanceFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                if (!Utils.isValidClass(mAbstractProcessor.mMessager, element)) return;
                ClassName currentType = ClassName.get(element);
                if (mList.contains(currentType)) continue;
                mList.add(currentType);
                String className = null;
                try {
                    Class<?> clazz = element.getAnnotation(InstanceFactory.class).clazz();
                    className = clazz.getCanonicalName();
                } catch (MirroredTypeException mte) {
                    DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                    TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                    className = classTypeElement.getQualifiedName().toString();
                } catch (Exception e) {
                }
                if (className != null && !className.equals(InstanceFactory.class.getName())) {
                    blockBuilder1.addStatement("case $S: return  new $T()", currentType.simpleName(), Utils.getType(className));//初始化Repository
                } else {
                    int type = element.getAnnotation(InstanceFactory.class).type();
                    if (type == InstanceFactory.typeDefault)
                        blockBuilder1.addStatement("case $S: return  new $T()", currentType.simpleName(), currentType);//初始化Presenter
                    else if (type == InstanceFactory.typeVH) {
                        blockBuilder1.addStatement("case $S: return new $T(view)", currentType.simpleName(), currentType);//初始化傀儡ViewHolder，会被缓存的，全局单例
                        blockBuilder2.addStatement("case $S: return new $T(view)", currentType.simpleName(), currentType);//初始化真正的ViewHolder
                    }
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
