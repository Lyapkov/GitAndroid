package com.example.android2.mvp.model.storage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single


interface IImageStorage {
    fun contains(url: String): Single<Boolean>
    fun getBytes(url: String): Maybe<ByteArray?>
    fun saveImage(url: String, bytes: ByteArray): Completable
}