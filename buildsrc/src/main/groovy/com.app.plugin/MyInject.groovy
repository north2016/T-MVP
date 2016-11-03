package com.app.plugin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import org.gradle.api.Project

import java.lang.annotation.Annotation

public class MyInject {
    private final static ClassPool pool = ClassPool.getDefault()
    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";
    final static String LogTimeAnnotation = "com.app.annotation.javassist.LogTime";

    public static void injectDir(String path, String packageName, Project project) {
        pool.appendClassPath(path)
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class")) {
                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName);
                    boolean isMyPackage = index != -1;
                    if (isMyPackage) {
                        int end = filePath.length() - 6 // .class = 6
                        String className = filePath.substring(index, end).replace('\\', '.').replace('/', '.')
                        CtClass c = pool.getCtClass(className)
                        if (c.isFrozen()) {
                            c.defrost()
                        }
                        pool.importPackage("android.support.v7.app.AppCompatActivity");
                        pool.importPackage(LogTimeAnnotation);
                        for (CtMethod ctmethod : c.getMethods()) {
                            String methodName = ctmethod.getName();
                            methodName = methodName.substring(
                                    methodName.lastIndexOf('.') + 1, methodName.length());
                            for (Annotation mAnnotation : ctmethod.getAnnotations()) {
                                if (mAnnotation.annotationType().canonicalName.equals(LogTimeAnnotation)) {
                                    //开始修改class文件
                                    project.logger.error "开始修改class文件!" + className + "." + methodName
                                    String outputStr = "\n System.out.println(\"this method " + className + "." + methodName + " cost:\" +(endTime - startTime) +\"ms.\");";
                                    String newMethodName = methodName + '$' + "impl";
//新定义一个方法叫做比如sayHello$impl
                                    ctmethod.setName(newMethodName);//原来的方法改个名字
                                    project.logger.error "替换老方法!" + className + "." + methodName + "改名为" + newMethodName
                                    CtMethod newMethod = CtNewMethod.copy(ctmethod,
                                            methodName, c, null);//创建新的方法，复制原来的方法 ，名字为原来的名字
                                    //构建新的方法体
                                    StringBuilder bodyStr = new StringBuilder();
                                    bodyStr.append("{");
                                    bodyStr.append(prefix);
                                    bodyStr.append(newMethodName + "(" + '$$' + ");\n");
//调用原有代码，类似于method();($$)表示所有的参数
                                    bodyStr.append(postfix);
                                    bodyStr.append(outputStr);
                                    bodyStr.append("}");
                                    newMethod.setBody(bodyStr.toString());//替换新方法
                                    project.logger.error "狸猫换太子!" + className + "." + methodName + "调用" + newMethodName
                                    c.addMethod(newMethod);//增加新方法，在这里已经狸猫换太子
                                    project.logger.error "增加新方法!" + className + "新增" + methodName
                                    c.writeFile(path)
                                    c.detach()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}