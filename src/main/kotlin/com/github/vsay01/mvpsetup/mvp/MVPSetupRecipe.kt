package com.github.vsay01.mvpsetup.mvp

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import com.github.vsay01.mvpsetup.listeners.MyProjectManagerListener.Companion.projectInstance
import com.github.vsay01.mvpsetup.mvp.template.classes.createActivity
import com.github.vsay01.mvpsetup.mvp.template.classes.createContract
import com.github.vsay01.mvpsetup.mvp.template.classes.createFragment
import com.github.vsay01.mvpsetup.mvp.template.classes.createPresenter
import com.github.vsay01.mvpsetup.mvp.template.layout.createActivityLayout
import com.github.vsay01.mvpsetup.mvp.template.layout.createFragmentLayout
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.idea.KotlinLanguage

fun RecipeExecutor.mvpSetup(
        moduleData: ModuleTemplateData,
        packageName: String,
        className: String,
        activityLayoutName: String,
        fragmentLayoutName: String
) {
    val (projectData) = moduleData
    val project = projectInstance ?: return

    addAllKotlinDependencies(moduleData)

    val virtualFiles = ProjectRootManager.getInstance(project).contentSourceRoots
    val virtSrc = virtualFiles.first { it.path.contains("src") }
    val virtRes = virtualFiles.first { it.path.contains("res") }
    val directorySrc = PsiManager.getInstance(project).findDirectory(virtSrc)!!
    val directoryRes = PsiManager.getInstance(project).findDirectory(virtRes)!!

    val activityClass = "${className}Activity".capitalize()
    val fragmentClass = "${className}Fragment".capitalize()
    val contractClass = "${className}Contract".capitalize()
    val presenterClass = "${className}Presenter".capitalize()
    val activityTitle = "$className Activity".capitalize()

    // This will generate new manifest (with activity) to merge it with existing
    generateManifest(moduleData, activityClass, activityTitle, packageName,
            isLauncher = false, hasNoActionBar = true, generateActivityTitle = true)

    createActivity(packageName, className, activityLayoutName, projectData)
            .save(directorySrc, packageName, "${activityClass}.kt")

    createContract(packageName, className)
            .save(directorySrc, packageName, "${contractClass}.kt")

    createFragment(packageName, className, fragmentLayoutName, projectData)
            .save(directorySrc, packageName, "${fragmentClass}.kt")

    createPresenter(packageName, className)
            .save(directorySrc, packageName, "${presenterClass}.kt")

    createActivityLayout(packageName, className)
            .save(directoryRes, "layout", "${activityLayoutName}_layout.xml")

    createFragmentLayout()
            .save(directoryRes, "layout", "${fragmentLayoutName}_layout.xml")
}

fun String.save(srcDir: PsiDirectory, subDirPath: String, fileName: String) {
    try {
        val destDir = subDirPath.split(".").toDir(srcDir)
        val psiFile = PsiFileFactory
                .getInstance(srcDir.project)
                .createFileFromText(fileName, KotlinLanguage.INSTANCE, this)
        destDir.add(psiFile)
    } catch (exc: Exception) {
        exc.printStackTrace()
    }
}

fun List<String>.toDir(srcDir: PsiDirectory): PsiDirectory {
    var result = srcDir
    forEach {
        result = result.findSubdirectory(it) ?: result.createSubdirectory(it)
    }
    return result
}