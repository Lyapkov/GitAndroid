package com.example.android2.mvp.model.storage.room

import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.entity.room.RoomGithubUser
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.storage.IUserStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserStorage(val db: Database) : IUserStorage {
    override fun putUsers(users: List<GithubUser>): Completable =
        Completable.fromAction {
            val roomUsers = users.map { user ->
                RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
            }
            db.userDao.insert(roomUsers)
        }.subscribeOn(Schedulers.io())

    override fun getUsers(): Single<List<GithubUser>> =
        Single.fromCallable {
            db.userDao.getAll().map { roomUser ->
                GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
            }
        }.subscribeOn(Schedulers.io())
}