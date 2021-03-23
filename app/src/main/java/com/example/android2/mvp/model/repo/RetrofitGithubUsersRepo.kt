package com.example.android2.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.android2.mvp.model.api.IDataSource

class RetrofitGithubUsersRepo(val api: IDataSource) : IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
    override fun getUserRepos(url: String) = api.getUserRepos(url).subscribeOn(Schedulers.io())
}