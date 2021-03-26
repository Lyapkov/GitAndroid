package com.example.android2.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}