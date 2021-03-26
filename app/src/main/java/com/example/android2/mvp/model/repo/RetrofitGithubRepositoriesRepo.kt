package com.example.android2.mvp.model.repo

import com.example.android2.mvp.model.api.IDataSource
import com.example.android2.mvp.model.entity.GithubRepository
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.entity.room.RoomGithubRepository
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if(isOnline){
            user.reposUrl?.let { url ->
                api.getRepositories(url)
                    .flatMap { repositories ->
                        Single.fromCallable {
                            val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
                            val roomRepos = repositories.map { repo ->
                                RoomGithubRepository(repo.id, repo.name, repo.forksCount, roomUser.id)
                            }
                            db.repositoryDao.insert(roomRepos)
                            repositories
                        }
                    }
            } ?: Single.error(RuntimeException("User has no repos url"))
        } else {
            Single.fromCallable {
                val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
                db.repositoryDao.findForUser(roomUser.id).map { roomRepo ->
                    GithubRepository(roomRepo.id, roomRepo.name, roomRepo.forksCount)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

}