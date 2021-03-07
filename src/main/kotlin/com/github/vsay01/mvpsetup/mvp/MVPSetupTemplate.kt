package com.github.vsay01.mvpsetup.mvp

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.activityToLayout
import com.android.tools.idea.wizard.template.fragmentToLayout
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template

val mviSetupTemplate
	get() = template {
		revision = 2
		name = "MVP Setup"
		description = "Generate files for MVP"
		minApi = 16
		minBuildApi = 16
		category = Category.Other // Check other categories
		formFactor = FormFactor.Mobile
		screens = listOf(
			WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry,
			WizardUiContext.NewProject, WizardUiContext.NewModule
		)

		val packageNameParam = defaultPackageNameParameter
		val className = stringParameter {
			name = "Class Name"
			default = "Titi"
			help = "The name of the class to create and use in Activity"
			constraints = listOf(Constraint.NONEMPTY)
		}

		val activityLayoutName = stringParameter {
			name = "Activity Layout Name"
			default = "Titi"
			help = "The name of the layout to create for the activity"
			constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
			suggest = { activityToLayout(className.value.toLowerCase()) }
		}

		val fragmentLayoutName = stringParameter {
			name = "Fragment Layout Name"
			default = "Toto"
			help = "The name of the layout to create for the fragment"
			constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
			suggest = { fragmentToLayout(className.value.toLowerCase()) }
		}

		widgets(
			TextFieldWidget(className),
			TextFieldWidget(activityLayoutName),
			TextFieldWidget(fragmentLayoutName),
			PackageNameWidget(packageNameParam)
		)

		recipe = { data: TemplateData ->
			mvpSetup(
				data as ModuleTemplateData,
				packageNameParam.value,
				className.value,
				activityLayoutName.value,
				fragmentLayoutName.value
			)
		}
	}

val defaultPackageNameParameter
	get() = stringParameter {
		name = "Package name"
		visible = { !isNewModule }
		default = "com.example.myapp"
		constraints = listOf(Constraint.PACKAGE)
		suggest = { packageName }
	}
