package com.storytoys.disney.pixar.coloring.princess.googlep

import android.app.Application
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AndroidAppContainer
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder

class RepCraftApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainerHolder.instance = AndroidAppContainer(this)
    }
}
