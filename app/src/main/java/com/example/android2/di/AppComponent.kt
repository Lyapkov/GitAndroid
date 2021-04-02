package com.example.android2.di

import com.example.android2.di.module.*
import com.example.android2.mvp.presenter.MainPresenter
import com.example.android2.mvp.presenter.RepositoryPresenter
import com.example.android2.mvp.presenter.UserPresenter
import com.example.android2.mvp.presenter.UsersPresenter
import com.example.android2.ui.activity.MainActivity
import com.example.android2.ui.adapter.UsersRVAdapter
import com.example.android2.ui.image.GlideImageLoader
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        ApiModule::class,
        RepoModule::class,
        StorageModule::class,
        SchedulersModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
}