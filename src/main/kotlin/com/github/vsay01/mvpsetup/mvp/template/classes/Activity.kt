package com.github.vsay01.mvpsetup.mvp.template.classes

import com.android.tools.idea.wizard.template.ProjectTemplateData

fun createActivity(
    packageName: String,
    className: String,
    activityLayoutName: String,
    projectData: ProjectTemplateData
) = """
	package $packageName
	
	import 	${projectData.applicationPackage}.R
	
	//todo create BaseActivity and import to this class
	class ${className}Activity : BaseActivity(), ${className}Fragment.On${className}FragmentInteractionListener {
	
		private lateinit var presenter: ${className}Contract.Presenter
	
		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setContentView(R.layout.${activityLayoutName}_layout)
	
			val ${className.toLowerCase()}Fragment = ${className}Fragment.newInstance()
			supportFragmentManager
					.beginTransaction()
					.replace(R.id.frame_layout_content, ${className.toLowerCase()}Fragment, "${className}Fragment")
					.addToBackStack(null)
					.commit()
	
			presenter = ${className}Presenter(this, ${className.toLowerCase()}Fragment)
		}
	
		override fun onStart() {
			super.onStart()
			presenter.start()
		}
	
		override fun onStop() {
			super.onStop()
			presenter.stop()
		}
	
		override fun on${className}FragmentInteraction() {
	
		}
	}
""".trimIndent()
