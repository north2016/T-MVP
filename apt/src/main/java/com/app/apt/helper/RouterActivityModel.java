package com.app.apt.helper;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by baixiaokang on 16/12/30.
 */

public class RouterActivityModel {
    boolean isNeedBind;//是否需要对目标类进行数据绑定，只有有传参数的和有转场需要初始化数据绑定
    TypeElement element;//当前的Activity
    String actionName;//当前Activity的ActionName
    String SceneTransitionElementName;//转场元素View的名称
    Element SceneTransitionElement;//转场元素View的Element
    List<Element> ExtraElements;//需要传递的参数的Extra元素Element列表
    List<String> ExtraElementKeys;//需要传递的参数的Extra元素Element列表的Key列表

    public String getSceneTransitionElementName() {
        return SceneTransitionElementName;
    }

    public void setSceneTransitionElementName(String sceneTransitionElementName) {
        SceneTransitionElementName = sceneTransitionElementName;
    }

    public List<String> getExtraElementKeys() {
        return ExtraElementKeys;
    }

    public void setExtraElementKeys(List<String> extraElementKeys) {
        ExtraElementKeys = extraElementKeys;
    }

    public List<Element> getExtraElements() {
        return ExtraElements;
    }

    public void setExtraElements(List<Element> extraElements) {
        ExtraElements = extraElements;
    }

    public Element getSceneTransitionElement() {
        return SceneTransitionElement;
    }

    public void setSceneTransitionElement(Element sceneTransitionElement) {
        SceneTransitionElement = sceneTransitionElement;
    }

    public TypeElement getElement() {
        return element;
    }

    public void setElement(TypeElement mElement) {
        this.element = mElement;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String mActionName) {
        this.actionName = mActionName;
    }

    public boolean isNeedBind() {
        return isNeedBind;
    }

    public void setNeedBind(boolean needBind) {
        isNeedBind = needBind;
    }
}
