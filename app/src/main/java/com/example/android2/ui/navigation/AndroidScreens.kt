package com.example.android2.ui.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.navigation.IScreens
import com.example.android2.ui.fragment.UserFragment
import com.example.android2.ui.fragment.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(githubUser: GithubUser) = FragmentScreen { UserFragment.newInstance(githubUser) }
}