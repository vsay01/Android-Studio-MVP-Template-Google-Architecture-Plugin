package com.github.vsay01.mvpsetup.mvp

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider

class 	WizardTemplateProviderImpl : WizardTemplateProvider() {
	override fun getTemplates(): List<Template> = listOf(mviSetupTemplate)
}