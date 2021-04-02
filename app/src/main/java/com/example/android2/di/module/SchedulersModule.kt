package com.example.android2.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Named

@Module
class SchedulersModule {

    @Named("uiScheduler")
    @Provides
    fun uiScheduler(): Scheduler =
        AndroidSchedulers.mainThread()
}