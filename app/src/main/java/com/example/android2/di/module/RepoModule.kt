package com.example.android2.di.module

import com.example.android2.mvp.model.api.IDataSource
import com.example.android2.mvp.model.network.INetworkStatus
import com.example.android2.mvp.model.repo.IGithubUsersRepo
import com.example.android2.mvp.model.repo.RetrofitGithubUsersRepo
import com.example.android2.mvp.model.storage.IUsersStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        storage: IUsersStorage
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, storage)

}