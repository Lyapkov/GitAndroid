package com.example.android2.mvp.presenter

import com.example.android2.mvp.model.entity.GithubRepository
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import com.example.android2.mvp.model.entity.GithubUser
import com.example.android2.mvp.model.navigation.IScreens
import com.example.android2.mvp.model.repo.IGithubRepositoriesRepo
import com.example.android2.mvp.presenter.list.IRepositoryListPresenter
import com.example.android2.mvp.view.UserView
import com.example.android2.mvp.view.list.RepositoryItemView
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class UserPresenter(
    val user: GithubUser
) : MvpPresenter<UserView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var screens: IScreens
    @Inject
    lateinit var repositoriesRepo: IGithubRepositoriesRepo
    @field:Named("uiScheduler")
    @Inject
    lateinit var uiScheduler: Scheduler


    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GithubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null
        override fun getCount() = repositories.size

        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }
    }

    val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        repositoriesListPresenter.itemClickListener = { itemView ->
            val repository = repositoriesListPresenter.repositories[itemView.pos]
            router.navigateTo(screens.repository(repository))
        }
    }

    fun loadData() {
        repositoriesRepo.getRepositories(user)
            .observeOn(uiScheduler)
            .subscribe({ repositories ->
                repositoriesListPresenter.repositories.clear()
                repositoriesListPresenter.repositories.addAll(repositories)
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
