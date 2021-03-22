package com.example.android2.mvp.model.image

interface IImageLoader<T> {
    fun load(url: String, container: T)
}