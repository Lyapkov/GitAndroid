package com.example.android2.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import com.example.android2.mvp.model.entity.GithubUser

interface IDataSource {

    @GET("users")
    fun getUsers() : Single<List<GithubUser>>

//    @GET
//    fun getUserRepos(@Url url: String) : Single

}