package com.example.android2.di.module

import androidx.room.Room
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.storage.IUsersStorage
import com.example.android2.mvp.model.storage.room.UsersStorage
import com.example.android2.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun usersStorage(db: Database): IUsersStorage = UsersStorage(db)
}