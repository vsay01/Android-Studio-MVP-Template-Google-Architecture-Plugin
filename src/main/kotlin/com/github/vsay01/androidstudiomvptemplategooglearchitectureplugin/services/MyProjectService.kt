package com.github.vsay01.androidstudiomvptemplategooglearchitectureplugin.services

import com.github.vsay01.androidstudiomvptemplategooglearchitectureplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
