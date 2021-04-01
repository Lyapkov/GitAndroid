package com.example.android2.mvp.model.storage

import com.example.android2.mvp.model.entity.GithubRepository
import com.example.android2.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRepositoryStorage {
    fun putRepositories(
        user: GithubUser,
        repositories: List<GithubRepository>
    ): Completable

    fun getRepositories(user: GithubUser): Single<List<GithubRepository>>
}