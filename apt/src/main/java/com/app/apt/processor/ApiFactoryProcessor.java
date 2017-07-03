package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.FilerException;
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

public class ApiFactoryProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        String CLASS_NAME = "ApiFactory";
        String DATA_ARR_CLASS = "DataArr";
        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ API工厂 此类由apt自动生成");
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                for (Element e : element.getEnclosedElements()) {
                    ExecutableElement executableElement = (ExecutableElement) e;
                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addJavadoc("@此方法由apt自动生成")
                                    .addModifiers(PUBLIC, STATIC);

                    if (TypeName.get(executableElement.getReturnType()).toString().contains(DATA_ARR_CLASS)) {//返回列表数据
                        methodBuilder.returns(ClassName.get("io.reactivex", "Flowable"));
                        Map<String, Object> params = new HashMap<>();
                        methodBuilder.addParameter(params.getClass(), "param");
                        ClassName apiUtil = ClassName.get("com.base.util", "ApiUtil");
                        ClassName C = ClassName.get("com", "C");
                        CodeBlock.Builder blockBuilder = CodeBlock.builder();
                        int len = executableElement.getParameters().size();
                        for (int i = 0; i < len; i++) {
                            VariableElement ep = executableElement.getParameters().get(i);
                            boolean isLast = i == len - 1;
                            String split = (isLast ? "" : ",");
                            switch (ep.getSimpleName().toString()) {
                                case "include":
                                    blockBuilder.add("$L.getInclude(param)" + split, apiUtil);
                                    break;
                                case "where":
                                    blockBuilder.add("$L.getWhere(param)" + split, apiUtil);
                                    break;
                                case "skip":
                                    blockBuilder.add("$L.getSkip(param)" + split, apiUtil);
                                    break;
                                case "limit":
                                    blockBuilder.add("$L.PAGE_COUNT" + split, C);
                                    break;
                                case "order":
                                    blockBuilder.add("$L._CREATED_AT" + split, C);
                                    break;
                            }
                        }
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".service.$L($L)" +
                                        ".compose($T.io_main())"
                                , ClassName.get("com.api", "Api")
                                , e.getSimpleName().toString()
                                , blockBuilder.build().toString()
                                , ClassName.get("com.base.util.helper", "RxSchedulers"));
                        tb.addMethod(methodBuilder.build());
                    } else {//返回普通数据
                        methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
                        String paramsString = "";
                        for (VariableElement ep : executableElement.getParameters()) {
                            methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                            paramsString += ep.getSimpleName().toString() + ",";
                        }
                        methodBuilder.addStatement(
                                "return $T.getInstance()" +
                                        ".service.$L($L)" +
                                        ".compose($T.io_main())"
                                , ClassName.get("com.api", "Api")
                                , e.getSimpleName().toString()
                                , paramsString.substring(0, paramsString.length() - 1)
                                , ClassName.get("com.base.util.helper", "RxSchedulers"));
                        tb.addMethod(methodBuilder.build());
                    }
                }
            }
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
