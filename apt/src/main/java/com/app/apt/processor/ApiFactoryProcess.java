package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.NoPackageNameException;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/12/28.
 */

public class ApiFactoryProcess implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        TypeElement mElement = null;
        String CLASS_NAME = "ApiFactory"; // 设置你要生成的代码class名字
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ API工厂 此类由apt自动生成");
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                if (mElement == null) mElement = element;
                for (Element e : element.getEnclosedElements()) {
                    ExecutableElement executableElement = (ExecutableElement) e;
                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addJavadoc("@此方法由apt自动生成")
                                    .returns(TypeName.get(executableElement.getReturnType())).addModifiers(PUBLIC, STATIC);
                    String paramsString = "";
                    for (VariableElement ep : executableElement.getParameters()) {
                        methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                        paramsString += ep.getSimpleName().toString() + ",";
                    }
                    ClassName apiClassName = ClassName.get("com.api", "Api");
                    ClassName rxSchedulersClassName = ClassName.get("com.base.util.helper", "RxSchedulers");
                    methodBuilder.addStatement(
                            "return $L.getInstance()" +
                                    ".service.$L($L)" +
                                    ".compose($T.io_main())"
                            , apiClassName.simpleName()
                            , e.getSimpleName().toString()
                            , paramsString.substring(0, paramsString.length() - 1)
                            , rxSchedulersClassName);
                    tb.addMethod(methodBuilder.build());
                }
            }
            if (mElement == null) return;
            String packageName = Utils.getPackageName(mAbstractProcessor.mElements, mElement);
            JavaFile javaFile = JavaFile.builder(packageName, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (NoPackageNameException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
