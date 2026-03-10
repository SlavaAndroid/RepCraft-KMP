package com.storytoys.disney.pixar.coloring.princess.googlep.core.di

import com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.core.di.AppContainer

object AppContainerHolder {
    private var _instance: AppContainer? = null

    var instance: AppContainer
        get() = _instance ?: error("AppContainer not initialized. Initialize before use.")
        set(value) { _instance = value }
}