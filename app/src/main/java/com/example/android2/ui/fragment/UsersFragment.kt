package com.example.android2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android2.databinding.FragmentUsersBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import com.example.android2.mvp.model.api.ApiHolder
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.repo.RetrofitGithubUsersRepo
import com.example.android2.mvp.presenter.UsersPresenter
import com.example.android2.mvp.view.UsersView
import com.example.android2.ui.App
import com.example.android2.ui.BackButtonListener
import com.example.android2.ui.adapter.UsersRVAdapter
import com.example.android2.ui.image.GlideImageLoader
import com.example.android2.ui.navigation.AndroidScreens
import com.example.android2.ui.network.AndroidNetworkStatus

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepo(
                ApiHolder.api,
                AndroidNetworkStatus(App.instance),
                Database.getInstance()
            ),
            App.instance.router, AndroidScreens(),
        )
    }

    var adapter: UsersRVAdapter? = null

    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}