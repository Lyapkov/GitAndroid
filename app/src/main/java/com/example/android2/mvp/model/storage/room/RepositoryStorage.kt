package com.example.android2.mvp.model.storage.room

import com.example.android2.mvp.model.entity.GithubRepository
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.entity.room.RoomGithubRepository
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.storage.IRepositoryStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryStorage(val db: Database) : IRepositoryStorage {
    override fun putRepositories(
        user: GithubUser,
        repositories: List<GithubRepository>
    ): Completable =
        Completable.fromAction {
            val roomUser =
                db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
            val roomRepos = repositories.map { repo ->
                RoomGithubRepository(repo.id, repo.name, repo.forksCount, roomUser.id)
            }
            db.repositoryDao.insert(roomRepos)
        }.subscribeOn(Schedulers.io())

    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> =
        Single.fromCallable {
            val roomUser =
                db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
            db.repositoryDao.findForUser(roomUser.id).map { roomRepo ->
                GithubRepository(roomRepo.id, roomRepo.name, roomRepo.forksCount)
            }
        }.subscribeOn(Schedulers.io())
}