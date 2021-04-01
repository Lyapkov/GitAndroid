package com.example.android2.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.android2.mvp.model.image.IImageLoader
import com.example.android2.mvp.model.network.INetworkStatus
import com.example.android2.mvp.model.storage.IImageStorage
import java.io.ByteArrayOutputStream

class GlideImageLoader(val storage: IImageStorage, val networkStatus: INetworkStatus) : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    //Обработка провала загрузки
                    return false
                }

                override fun onResourceReady(
                    bitmap: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val compressFormat = if (url.contains(".jpg")) Bitmap.CompressFormat.JPEG else Bitmap.CompressFormat.PNG
                    val stream = ByteArrayOutputStream()
                    bitmap?.compress(compressFormat, 100, stream)
                    val bytes = stream.use { it.toByteArray() }
                    storage.saveImage(url, bytes).blockingAwait()
                    return false
                }
            })
            .into(container)
    }
}