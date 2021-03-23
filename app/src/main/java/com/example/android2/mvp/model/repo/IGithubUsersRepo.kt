package com.example.android2.mvp.model.repo

import com.example.android2.mvp.model.entity.GithubRepos
import io.reactivex.rxjava3.core.Single
import com.example.android2.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepos(url: String): Single<List<GithubRepos>>
}