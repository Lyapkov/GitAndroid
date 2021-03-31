package com.example.android2.mvp.model.entity.room.dao

import androidx.room.*
import com.example.android2.mvp.model.entity.room.RoomGithubImage
import com.example.android2.mvp.model.entity.room.RoomGithubUser

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: RoomGithubImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg images: RoomGithubImage)

    @Update
    fun update(image: RoomGithubImage)

    @Update
    fun update(vararg images: RoomGithubImage)

    @Delete
    fun delete(image: RoomGithubImage)

    @Delete
    fun delete(vararg images: RoomGithubImage)

    @Query("SELECT * FROM RoomGithubImage WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): RoomGithubImage?
}