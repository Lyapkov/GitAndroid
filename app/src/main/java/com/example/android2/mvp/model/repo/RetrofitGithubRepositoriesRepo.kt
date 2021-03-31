package com.example.android2.mvp.model.repo

import com.example.android2.mvp.model.api.IDataSource
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.network.INetworkStatus
import com.example.android2.mvp.model.storage.IRepositoryStorage
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val storage: IRepositoryStorage
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepositories(url)
                        .flatMap { repositories ->
                            storage.putRepositories(user, repositories)
                                .toSingleDefault(repositories)
                        }
                } ?: Single.error(RuntimeException("User has no repos url"))
            } else {
                storage.getRepositories(user)
            }
        }.subscribeOn(Schedulers.io())

}