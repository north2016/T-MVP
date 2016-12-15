package com.app.apt.processor;

import com.app.annotation.apt.Repository;
import com.app.annotation.aspect.MemoryCache;
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

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/12/15.
 */

public class RepositoryProcess implements IProcessor {
    //逻辑很简单,这里直接生成代码,就没有封装成面向对象的方式
    @Override
    public void process(RoundEnvironment roundEnv, Filer mFiler, Elements mElements, Messager mMessager) {
        TypeElement mElement = null;
        String CLASS_NAME = "RepositoryFactory"; // 设置你要生成的代码class名字
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ 实例化工厂 此类由apt自动生成");
        MethodSpec.Builder methodBuilder1 = MethodSpec.methodBuilder("create").addAnnotation(MemoryCache.class)
                .addJavadoc("@此方法由apt自动生成")
                .returns(Object.class).addModifiers(PUBLIC, STATIC)
                .addParameter(Class.class, "mClass");
        List<ClassName> mList = new ArrayList<>();
        CodeBlock.Builder blockBuilder1 = CodeBlock.builder();
        blockBuilder1.beginControlFlow(" switch (mClass.getSimpleName())");//括号开始
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Repository.class))) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                if (mElement == null) mElement = element;
                if (!Utils.isValidClass(mMessager, element)) return;
                ClassName currentType = ClassName.get(element);
                if (mList.contains(currentType)) continue;
                mList.add(currentType);
                String qualifiedSuperClassName;
                try {
                    Class<?> clazz = element.getAnnotation(Repository.class).clazz();
                    qualifiedSuperClassName = clazz.getCanonicalName();
                } catch (MirroredTypeException mte) {
                    DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                    TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                    qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
                }
                blockBuilder1.addStatement("case $S: return new $L()", currentType.simpleName(), qualifiedSuperClassName);
            }
            blockBuilder1.addStatement("default: return null");
            blockBuilder1.endControlFlow();
            methodBuilder1.addCode(blockBuilder1.build());

            tb.addMethod(methodBuilder1.build());
            if (mElement == null) {
                // mMessager.printMessage(Diagnostic.Kind.ERROR, "apt处理失败!");
                return;
            }
            String packageName = Utils.getPackageName(mElements, mElement);
            JavaFile javaFile = JavaFile.builder(packageName, tb.build()).build();// 生成源代码
            javaFile.writeTo(mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
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
