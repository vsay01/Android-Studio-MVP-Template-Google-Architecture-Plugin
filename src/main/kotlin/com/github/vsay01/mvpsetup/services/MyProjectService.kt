package com.github.vsay01.mvpsetup.services

import com.intellij.openapi.project.Project
import com.github.vsay01.mvpsetup.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
