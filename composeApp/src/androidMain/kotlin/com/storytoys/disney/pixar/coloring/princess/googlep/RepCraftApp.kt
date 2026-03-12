package com.storytoys.disney.pixar.coloring.princess.googlep

import android.app.Application
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AndroidAppContainer
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import com.storytoys.disney.pixar.coloring.princess.googlep.network.SessionsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepCraftApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainerHolder.instance = AndroidAppContainer(this)
        CoroutineScope(Dispatchers.IO).launch {
            SessionsService.checkSessions()
        }
    }
}
