package com.example.android2.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import com.example.android2.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}