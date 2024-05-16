package com.example.catapi.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(
                okHttpClientModule,
                networkModule,
                remoteDataSourceModule,
                mapperModule,
                repositoryModule,
                useCaseModule,
                mainModule
            )
        }
    }
}