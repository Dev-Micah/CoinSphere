package com.micahnyabuto.coinsphere

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoinSphereApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}