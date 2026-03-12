package com.storytoys.disney.pixar.coloring.princess.googlep

import androidx.compose.ui.window.ComposeUIViewController
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.IosAppContainer
import com.storytoys.disney.pixar.coloring.princess.googlep.network.SessionsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

private val iosContainer by lazy { IosAppContainer() }

fun MainViewController() = run {
    AppContainerHolder.instance = iosContainer
    CoroutineScope(Dispatchers.IO).launch {
        SessionsService.checkSessions()
    }
    ComposeUIViewController { App() }
}
