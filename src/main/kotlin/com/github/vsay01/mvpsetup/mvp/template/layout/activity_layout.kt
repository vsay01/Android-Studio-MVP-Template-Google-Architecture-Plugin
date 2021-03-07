package com.github.vsay01.mvpsetup.mvp.template.layout

fun createActivityLayout(
    packageName: String,
    className: String
) = """
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/coordinator_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="$packageName.${className}Activity">

        <FrameLayout
            android:id="@+id/frame_layout_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </androidx.coordinatorlayout.widget.CoordinatorLayout>
""".trimIndent()
