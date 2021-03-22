package com.example.android2.mvp.view.list

interface UserItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}