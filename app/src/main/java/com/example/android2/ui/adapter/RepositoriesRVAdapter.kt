package com.example.android2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemRepositoryBinding
import com.example.android2.mvp.presenter.list.IRepositoryListPresenter
import com.example.android2.mvp.view.list.RepositoryItemView

class RepositoriesRVAdapter(val presenter: IRepositoryListPresenter) : RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(val vb: ItemRepositoryBinding) : RecyclerView.ViewHolder(vb.root), RepositoryItemView {
        override var pos = -1
        override fun setName(text: String) = with(vb) {
            tvName.text = text
        }
    }
}