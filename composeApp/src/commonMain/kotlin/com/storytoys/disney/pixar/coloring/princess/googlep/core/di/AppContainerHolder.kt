package com.storytoys.disney.pixar.coloring.princess.googlep.core.di

object AppContainerHolder {
    private var _instance: AppContainer? = null

    var instance: AppContainer
        get() = _instance ?: error("AppContainer not initialized. Initialize before use.")
        set(value) { _instance = value }
}