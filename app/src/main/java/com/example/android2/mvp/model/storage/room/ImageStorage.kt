package com.example.android2.mvp.model.storage.room

import com.example.android2.mvp.model.entity.room.RoomGithubImage
import com.example.android2.mvp.model.entity.room.db.Database
import com.example.android2.mvp.model.storage.IImageStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ImageStorage(val db: Database, val dir: File) : IImageStorage {
    override fun contains(url: String): Single<Boolean> = Single.fromCallable {
        db.imageDao.findByUrl(url) != null
    }.subscribeOn(Schedulers.io())

    override fun getBytes(url: String): Maybe<ByteArray?> = Maybe.fromCallable {
        db.imageDao.findByUrl(url)?.let { File(it.localPath).inputStream().readBytes() }
    }.subscribeOn(Schedulers.io())

    override fun saveImage(url: String, bytes: ByteArray): Completable = Completable.create{
        val fileFormat = if (url.contains(".jpg")) ".jpg" else ".png"
        val imageFile = File(dir, UUID.randomUUID().toString() + fileFormat)
        try {
            FileOutputStream(imageFile).use {
                it.write(bytes)
            }
        } catch (e: Exception) {
            it.onError(e)
        }
        db.imageDao.insert(RoomGithubImage(url, imageFile.path))
        it.onComplete()
    }.subscribeOn(Schedulers.io())
}