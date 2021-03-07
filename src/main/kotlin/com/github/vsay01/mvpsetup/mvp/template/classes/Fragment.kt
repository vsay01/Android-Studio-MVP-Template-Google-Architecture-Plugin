package com.github.vsay01.mvpsetup.mvp.template.classes

import com.android.tools.idea.wizard.template.ProjectTemplateData

fun createFragment(
    packageName: String,
    className: String,
    fragmentLayoutName: String,
    projectData: ProjectTemplateData
) = """
    package $packageName

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import ${projectData.applicationPackage}.R;

    // Your presenter is available using the mPresenter variable
    class ${className}Fragment : BaseFragment(), ${className}Contract.View {

        private var presenter: ${className}Contract.Presenter? = null

        override fun setPresenter(presenter: ${className}Contract.Presenter) {
            this.presenter = presenter
        }

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View?
                = inflater.inflate(R.layout.${fragmentLayoutName}_layout, container, false)

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         *
         *
         * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
         */
        interface On${className}FragmentInteractionListener {
            // TODO: Update argument type and name
            fun on${className}FragmentInteraction()
        }

        companion object {
            fun newInstance() = ${className}Fragment()
        }
    }// Required empty public constructor
""".trimIndent()
