package com.example.android2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemReposBinding
import com.example.android2.mvp.presenter.list.IReposListPresenter
import com.example.android2.mvp.view.list.ReposItemView

class ReposRVAdapter(val presenter: IReposListPresenter) :
    RecyclerView.Adapter<ReposRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(val vb: ItemReposBinding) : RecyclerView.ViewHolder(vb.root),
        ReposItemView {
        override var pos = -1

        override fun setName(text: String) = with(vb) {
            tvRepos.text = text
        }
    }
}