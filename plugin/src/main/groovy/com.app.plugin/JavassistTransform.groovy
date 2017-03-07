package com.app.plugin

import com.android.build.api.transform.*
import com.google.common.collect.Sets
import javassist.ClassPool
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class JavassistTransform extends Transform {
    Project project

    public JavassistTransform(Project project) {    // 构造函数，我们将Project保存下来备用
        this.project = project
    }

    @Override
    String getName() {// 设置我们自定义的Transform对应的Task名称
        return "JavassistTrans"
    }


    @Override
    // 指定输入的类型，通过这里的设定，可以指定我们要处理的文件类型这样确保其他类型的文件不会传入
    Set<QualifiedContent.ContentType> getInputTypes() {
        return Sets.immutableEnumSet(QualifiedContent.DefaultContentType.CLASSES)
    }


    @Override
// 指定Transform的作用范围
    Set<QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.PROJECT_LOCAL_DEPS,
                QualifiedContent.Scope.SUB_PROJECTS, QualifiedContent.Scope.SUB_PROJECTS_LOCAL_DEPS,
                QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        def startTime = System.currentTimeMillis();
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        inputs.each { TransformInput input ->
            try {
                input.jarInputs.each {
                    MyInject.injectDir(it.file.getAbsolutePath(), "com", project)
                    String outputFileName = it.name.replace(".jar", "") + '-' + it.file.path.hashCode()
                    def output = outputProvider.getContentLocation(outputFileName, it.contentTypes, it.scopes, Format.JAR)
                    FileUtils.copyFile(it.file, output)
                }
            } catch (Exception e) {
                project.logger.err e.getMessage()
            }
            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                MyInject.injectDir(directoryInput.file.absolutePath, "com", project)
                // 获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        ClassPool.getDefault().clearImportedPackages();
        project.logger.error("JavassistTransform cast :" + (System.currentTimeMillis() - startTime) / 1000 + " secs");
    }
}