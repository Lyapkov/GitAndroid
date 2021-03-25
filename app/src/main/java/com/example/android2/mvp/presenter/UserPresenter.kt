package com.example.android2.mvp.presenter

import com.example.android2.mvp.model.entity.GithubRepos
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.repo.IGithubUsersRepo
import com.example.android2.mvp.presenter.list.IReposListPresenter
import com.example.android2.mvp.view.UserView
import com.example.android2.mvp.view.list.ReposItemView
import io.reactivex.rxjava3.core.Scheduler

class UserPresenter(val uiScheduler: Scheduler, val usersRepo: IGithubUsersRepo, val router: Router, val user: GithubUser) : MvpPresenter<UserView>() {

    class ReposListPresenter : IReposListPresenter {
        val repos = mutableListOf<GithubRepos>()
        override var itemClickListener: ((ReposItemView) -> Unit)? = null

        override fun getCount() = repos.size

        override fun bindView(view: ReposItemView) {
            val repo = repos[view.pos]
            view.setName(repo.name)
        }
    }

    val reposListPresenter = ReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setLogin(user.login)
        viewState.init()
        loadData()
    }

    fun loadData() {
        usersRepo.getUserRepos(user.reposUrl)
            .observeOn(uiScheduler)
            .subscribe({ repos ->
                reposListPresenter.repos.clear()
                reposListPresenter.repos.addAll(repos)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
