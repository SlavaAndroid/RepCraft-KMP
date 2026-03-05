package com.storytoys.disney.pixar.coloring.princess.googlep

import androidx.compose.ui.window.ComposeUIViewController
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.IosAppContainer

private val iosContainer by lazy { IosAppContainer() }

fun MainViewController() = run {
    AppContainerHolder.instance = iosContainer
    ComposeUIViewController { App() }
}
