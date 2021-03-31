package com.example.android2.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.android2.mvp.model.api.IDataSource
import com.example.android2.mvp.model.network.INetworkStatus
import com.example.android2.mvp.model.storage.IUserStorage

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val storage: IUserStorage
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    storage.putUsers(users).toSingleDefault(users)
                }
        } else {
            storage.getUsers()
        }
    }.subscribeOn(Schedulers.io())
}