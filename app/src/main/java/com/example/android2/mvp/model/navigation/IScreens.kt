package com.example.android2.mvp.model.navigation

import com.github.terrakok.cicerone.Screen
import com.example.android2.mvp.model.entity.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(githubUser: GithubUser): Screen
}