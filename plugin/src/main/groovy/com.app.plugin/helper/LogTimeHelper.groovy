package com.app.plugin.helper

import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import org.gradle.api.Project;

/**
 * Created by baixiaokang on 16/11/15.
 */

public class LogTimeHelper {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";
    final static String LogTimeAnnotation = "com.app.annotation.javassist.LogTime";

    //  时间log先注释掉               if (mAnnotation.annotationType().canonicalName.equals(LogTimeHelper.LogTimeAnnotation))
//                                    LogTimeHelper.initLogTime(project, methodName, className, ctmethod, c, path);


    static void initLogTime(Project project, String methodName, String className, CtMethod ctmethod, CtClass c, String path) {
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
