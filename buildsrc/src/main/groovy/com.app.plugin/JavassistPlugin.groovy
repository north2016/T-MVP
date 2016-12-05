package com.app.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class JavassistPlugin implements Plugin<Project> {

    void apply(Project project) {
        def log = project.logger
        log.error "========================";
        log.error "Javassist开始修改Class!";
        log.error "========================";
        project.android.registerTransform(new JavassistTransform(project))
    }
}