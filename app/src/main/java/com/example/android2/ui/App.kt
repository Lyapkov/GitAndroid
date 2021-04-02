package com.example.android2.ui

import android.app.Application
import com.example.android2.di.AppComponent
import com.example.android2.di.DaggerAppComponent
import com.example.android2.di.module.AppModule
import com.example.android2.mvp.model.entity.room.db.Database

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
