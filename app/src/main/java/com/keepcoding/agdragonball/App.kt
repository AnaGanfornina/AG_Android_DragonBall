package com.keepcoding.agdragonball

import android.app.Application
import com.keepcoding.agdragonball.di.repositoryModule
import com.keepcoding.agdragonball.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // Comentamos temporalmente todo esto

        startKoin{
            androidContext(this@App)
            modules(repositoryModule, viewModelModule)
        }

    }
}