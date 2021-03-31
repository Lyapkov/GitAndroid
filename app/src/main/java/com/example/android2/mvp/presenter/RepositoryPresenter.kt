package com.example.android2.mvp.presenter

import com.example.android2.mvp.model.entity.GithubRepository
import com.example.android2.mvp.view.RepositoryView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class RepositoryPresenter(val router: Router, val githubRepository: GithubRepository) : MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id ?: "")
        viewState.setTitle(githubRepository.name ?: "")
        viewState.setForksCount(githubRepository.forksCount ?: "")
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}