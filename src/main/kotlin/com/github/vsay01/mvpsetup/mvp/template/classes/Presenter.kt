package com.github.vsay01.mvpsetup.mvp.template.classes

fun createPresenter(
		packageName: String,
		className: String
) = """
	package $packageName

	class ${className}Presenter(private val context: Context, private val view: ${className}Contract.View) : BasePresenter(), ${className}Contract.Presenter {

	    init {
	        this.view.setPresenter(this)
	    }

	    override fun start() {

	    }

	    override fun stop() {

	    }
	}
""".trimIndent()