package com.example.android2.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomGithubImage (
    @PrimaryKey val url: String,
    val localPath: String
)