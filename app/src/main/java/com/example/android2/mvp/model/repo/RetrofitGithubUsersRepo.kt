package com.example.android2.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.android2.mvp.model.api.IDataSource
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.entity.room.RoomGithubUser
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                        }
                        db.userDao.insert(roomUsers)
                        users
                    }
                }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map { roomUser ->
                    GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
                }
            }
        }
    }.subscribeOn(Schedulers.io())
}