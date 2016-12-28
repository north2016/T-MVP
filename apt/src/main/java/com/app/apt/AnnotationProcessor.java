package com.app.apt;

import com.app.apt.processor.ApiFactoryProcess;
import com.app.apt.processor.InstanceProcessor;
import com.app.apt.processor.RepositoryProcess;
import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)//自动生成 javax.annotation.processing.IProcessor 文件
@SupportedSourceVersion(SourceVersion.RELEASE_8)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "com.app.annotation.apt.Instance",
        "com.app.annotation.apt.Repository",
        "com.app.annotation.apt.ApiFactory"
})
public class AnnotationProcessor extends AbstractProcessor {
    public Filer mFiler = processingEnv.getFiler(); //文件相关的辅助类
    public Elements mElements = processingEnv.getElementUtils(); //元素相关的辅助类
    public Messager mMessager = processingEnv.getMessager(); //日志相关的辅助类

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        new InstanceProcessor().process(roundEnv, this);
        new RepositoryProcess().process(roundEnv, this);
        new ApiFactoryProcess().process(roundEnv, this);
        return true;
    }
}
