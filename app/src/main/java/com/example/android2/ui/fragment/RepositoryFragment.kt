package com.example.android2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android2.databinding.FragmentRepositoryBinding
import com.example.android2.mvp.model.entity.GithubRepository
import com.example.android2.mvp.presenter.RepositoryPresenter
import com.example.android2.mvp.view.RepositoryView
import com.example.android2.ui.App
import com.example.android2.ui.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    companion object {
        const val REPOSITORY_ARG = "repository"

        fun newInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }
        }
    }

    private var vb: FragmentRepositoryBinding? = null

    val presenter: RepositoryPresenter by moxyPresenter {
        val repository = arguments?.getParcelable<GithubRepository>(REPOSITORY_ARG) as GithubRepository
        RepositoryPresenter(repository).apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentRepositoryBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun init() {}

    override fun setId(text: String) {
        vb?.tvId?.text = text
    }

    override fun setTitle(text: String) {
        vb?.tvTitle?.text = text
    }

    override fun setForksCount(text: String) {
        vb?.tvForksCount?.text = text
    }

    override fun backPressed() = presenter.backPressed()
}