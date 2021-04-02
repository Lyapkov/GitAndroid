package com.example.android2.di.module

import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.room.Room
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.image.IImageLoader
import com.example.android2.mvp.model.network.INetworkStatus
import com.example.android2.mvp.model.storage.IImageStorage
import com.example.android2.mvp.model.storage.IRepositoryStorage
import com.example.android2.mvp.model.storage.IUsersStorage
import com.example.android2.mvp.model.storage.room.ImageStorage
import com.example.android2.mvp.model.storage.room.RepositoryStorage
import com.example.android2.mvp.model.storage.room.UsersStorage
import com.example.android2.ui.App
import com.example.android2.ui.image.GlideImageLoader
import com.example.android2.ui.network.AndroidNetworkStatus
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
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

    @Singleton
    @Provides
    fun repositoriesStorage(db: Database): IRepositoryStorage = RepositoryStorage(db)

    @Singleton
    @Provides
    fun imageStorage(db: Database, @Named("cacheDir") cacheDir: File): IImageStorage =
        ImageStorage(db, cacheDir)

    @Named("cacheDir")
    @Singleton
    @Provides
    fun cacheDir(app: App): File = app.cacheDir

    @Singleton
    @Provides
    fun imageLoader(
        storage: IImageStorage,
        networkStatus: INetworkStatus
    ): IImageLoader<ImageView> = GlideImageLoader(storage, networkStatus)
}