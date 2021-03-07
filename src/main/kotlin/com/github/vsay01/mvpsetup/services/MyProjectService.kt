package com.github.vsay01.mvpsetup.services

import com.github.vsay01.mvpsetup.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
