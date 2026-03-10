package com.storytoys.disney.pixar.coloring.princess.googlep.android_old_project.ui.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.storytoys.disney.pixar.coloring.princess.googlep.core.di.AppContainerHolder
import kotlinx.coroutines.flow.Flow

class NavViewModel : ViewModel() {
    private val container = AppContainerHolder.instance
    val onboardingComplete: Flow<Boolean> = container.onboardingStorage.onboardingComplete

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { NavViewModel() }
        }
    }
}