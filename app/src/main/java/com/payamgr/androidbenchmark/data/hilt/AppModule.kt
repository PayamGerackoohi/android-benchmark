package com.payamgr.androidbenchmark.data.hilt

import android.app.Activity
import com.payamgr.androidbenchmark.data.controller.SplashScreenController
import com.payamgr.androidbenchmark.data.controller.SplashScreenControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun splashScreenController(impl: SplashScreenControllerImpl): SplashScreenController
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppModulesProvider {
    fun splashScreenController(): SplashScreenController
}

fun Activity.provideAppModule() = EntryPoints.get(application, AppModulesProvider::class.java)
