package com.sleepyhead.patterns.mvi

import android.app.Application
import com.sleepyhead.patterns.mvi.data.AppContainer
import com.sleepyhead.patterns.mvi.data.DefaultAppContainer

class MviApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
