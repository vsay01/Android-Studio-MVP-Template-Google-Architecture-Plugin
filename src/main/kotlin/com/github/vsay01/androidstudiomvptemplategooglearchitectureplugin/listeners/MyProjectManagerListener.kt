package com.github.vsay01.androidstudiomvptemplategooglearchitectureplugin.listeners

import com.github.vsay01.androidstudiomvptemplategooglearchitectureplugin.services.MyProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
    }
}
