package com.example.android2.mvp.model.api

import com.example.android2.mvp.model.entity.GithubRepos
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import com.example.android2.mvp.model.entity.GithubUser
import retrofit2.http.Url

interface IDataSource {

    @GET("users")
    fun getUsers() : Single<List<GithubUser>>

    @GET
    fun getUserRepos(@Url url: String) : Single<List<GithubRepos>>

}