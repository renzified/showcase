package com.sleepyhead.patterns.mvvm

import android.app.Application
import com.sleepyhead.patterns.mvvm.data.AppContainer
import com.sleepyhead.patterns.mvvm.data.DefaultAppContainer

class MvvmApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
