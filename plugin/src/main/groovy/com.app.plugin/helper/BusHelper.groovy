package com.app.plugin.helper

import com.app.plugin.BusInfo
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.MethodInfo
import javassist.bytecode.annotation.IntegerMemberValue

import java.lang.annotation.Annotation

/**
 * Created by baixiaokang on 16/11/15.
 */

public class BusHelper {
    final static String OkBusAnnotation = "com.app.annotation.javassist.Bus"
    final static String OkBusRegisterAnnotation = "com.app.annotation.javassist.BusRegister"
    final static String OkBusUnRegisterAnnotation = "com.app.annotation.javassist.BusUnRegister"
    static def ON_CREATE = ['onCreate', "onActivityCreated"] as String[]
    static def ON_DESTROY = 'onDestroy'

    static def Activity_OnCreate = "\n" +
            "    protected void onCreate(Bundle savedInstanceState) {\n" +
            "        super.onCreate(savedInstanceState);\n";

    static def Fragment_OnCreate = "public void onActivityCreated(Bundle savedInstanceState) {\n" +
            "        super.onActivityCreated(savedInstanceState);"

    static def Pre_Switch_Str = "public void call(Message msg) {\n" +
            "switch (msg.what){\n"

    static def Pre_OnDestroy = "  \n" +
            "    protected void onDestroy() {\n" +
            "        super.onDestroy();\n";
    /**
     * 处理BusInfo
     * @param mBusInfo
     * @param path
     */
    static void intBus(BusInfo mBusInfo, String path) {
        if (mBusInfo.clazz.isFrozen()) mBusInfo.clazz.defrost()//解冻
        if (mBusInfo.BusRegisterMethod != null) {//有被BusRegister注解的方法
            mBusInfo.project.logger.quiet "BusRegisterMethod not null" +
            mBusInfo.BusRegisterMethod.insertAfter(getRegisterEventMethodStr(mBusInfo));
        } else if (mBusInfo.getOnCreateMethod() == null) {//没有OnCreateMethod，创建并加上新代码
            mBusInfo.project.logger.quiet "getOnCreateMethod  null "  + mBusInfo.isActivity
            String pre_create_str = mBusInfo.isActivity ? Activity_OnCreate : Fragment_OnCreate;
            String m = pre_create_str + getRegisterEventMethodStr(mBusInfo) + "}"
            mBusInfo.project.logger.quiet m
            CtMethod mInitEventMethod = CtNewMethod.make(m, mBusInfo.clazz);
            mBusInfo.clazz.addMethod(mInitEventMethod)
        } else {//有OnCreateMethod，直接插入新代码
            mBusInfo.project.logger.quiet "OnCreateMethod not null"
            mBusInfo.OnCreateMethod.insertAfter(getRegisterEventMethodStr(mBusInfo));
        }
        if (mBusInfo.BusUnRegisterMethod != null) {//有被BusUnRegister注解的方法
            mBusInfo.project.logger.quiet "BusUnRegisterMethod not null"
            mBusInfo.BusUnRegisterMethod.insertAfter(getUnRegisterEventMethodStr(mBusInfo));
        } else if (mBusInfo.OnDestroyMethod == null) {
            mBusInfo.project.logger.quiet "OnDestroyMethod null"
            String m = Pre_OnDestroy + getUnRegisterEventMethodStr(mBusInfo) + "}";
            mBusInfo.project.logger.quiet m
            CtMethod destroyMethod = CtNewMethod.make(m, mBusInfo.clazz)
            mBusInfo.clazz.addMethod(destroyMethod)
        } else {
            mBusInfo.project.logger.quiet "OnDestroyMethod not null"
            mBusInfo.OnDestroyMethod.insertAfter(getUnRegisterEventMethodStr(mBusInfo));
        }

        mBusInfo.clazz.writeFile(path)
    }

    /**
     * 获取初始化OkBus方法的代码
     * @param mBusInfo 事件信息
     * @return
     */
    static String getRegisterEventMethodStr(BusInfo mBusInfo) {
        String CreateStr = "";
        mBusInfo.clazz.addInterface(mBusInfo.clazz.classPool.get("com.base.event.Event"));//为当前的类添加时间处理的接口
        for (int i = 0; i < mBusInfo.getMethods().size(); i++) {
            MethodInfo methodInfo = mBusInfo.getMethods().get(i).getMethodInfo();
            Annotation mAnnotation = mBusInfo.getAnnotations().get(i)
            AnnotationsAttribute attribute = methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
            //获取注解属性
            javassist.bytecode.annotation.Annotation annotation = attribute.getAnnotation(mAnnotation.annotationType().canonicalName);
            //获取注解
            int id = ((IntegerMemberValue) annotation.getMemberValue("value")).getValue();//获取注解的值
            int thread = -1;
            if (annotation.getMemberValue("thread") != null)
                thread = ((IntegerMemberValue) annotation.getMemberValue("thread")).getValue();
            mBusInfo.eventIds.add(id)
            CreateStr += "OkBus.getInstance().register(" + id + ",(Event)this," + thread + ");\n"
        }
        initEventDispatch(mBusInfo)
        return CreateStr;
    }
    /**
     * 生成event事件分发的逻辑代码
     * @param mBusInfo
     * @return
     */
    static initEventDispatch(BusInfo mBusInfo) {
        String SwitchStr = Pre_Switch_Str;
        for (int i = 0; i < mBusInfo.eventIds.size(); i++) {
            CtMethod method = mBusInfo.getMethods().get(i)
            CtClass[] mParameterTypes = method.getParameterTypes();
            assert mParameterTypes.length <= 1
            boolean one = mParameterTypes.length == 1
            boolean isBaseType = false;
            String packageName = "";
            if (one) {
                String mParameterType = mParameterTypes[0].name;
                switch (mParameterType) {
                //Primitive Types（原始型）	Reference Types(Wrapper Class)（引用型，（包装类））
                    case "boolean": mParameterType = "Boolean"; isBaseType = true; break;
                    case "byte": mParameterType = "Byte"; isBaseType = true; break;
                    case "char": mParameterType = "Character"; isBaseType = true; break;
                    case "float": mParameterType = "Float"; isBaseType = true; break;
                    case "int": mParameterType = "Integer"; isBaseType = true; break;
                    case "long": mParameterType = "Long"; isBaseType = true; break;
                    case "short": mParameterType = "Short"; isBaseType = true; break;
                    case "double": mParameterType = "Double"; isBaseType = true; break;
                }
                mBusInfo.project.logger.quiet "name:" + mParameterType
                packageName = isBaseType ? "java.lang." + mParameterType : mParameterType;
                mBusInfo.clazz.classPool.importPackage(packageName)
            }//如果是基本数据类型，需要手动拆箱，否则会报错
            String ParamStr = isBaseType ? ("((" + packageName + ")msg.obj)." +
                    mParameterTypes[0].name + "Value()") : ("(" + packageName + ")msg.obj");
            SwitchStr += "case " + mBusInfo.eventIds.get(i) + ":" + method.getName() +
                    "(" + (one ? ParamStr : "") + ");\n break;\n"
        }
        String m = SwitchStr + "}\n}"
        mBusInfo.project.logger.quiet m
        CtMethod mDispatchEventMethod = CtMethod.make(m, mBusInfo.clazz);
        mBusInfo.clazz.addMethod(mDispatchEventMethod)
    }
    /**
     * 生成取消事件注册的代码
     * @param mBusInfo
     */
    static String getUnRegisterEventMethodStr(mBusInfo) {
        String dis_Str = "";
        mBusInfo.eventIds.each { id -> dis_Str += "OkBus.getInstance().unRegister(" + id + ");\n" }
        return dis_Str;
    }
}
