package com.micahnyabuto.coinsphere

import android.app.Application
import com.micahnyabuto.coinsphere.di.appModule
import com.micahnyabuto.coinsphere.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoinSphereApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoinSphereApp)
            modules(appModule, networkModule)
        }
    }
}