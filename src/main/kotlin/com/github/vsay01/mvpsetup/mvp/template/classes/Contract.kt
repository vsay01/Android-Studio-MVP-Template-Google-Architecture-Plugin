package com.github.vsay01.mvpsetup.mvp.template.classes

fun createContract(
    packageName: String,
    className: String
) = """
    package $packageName

    //todo create BaseContract and import to this class
    interface ${className}Contract {

        interface View : BaseContract.View<Presenter>

        interface Presenter : BaseContract.Presenter
    }
""".trimIndent()
