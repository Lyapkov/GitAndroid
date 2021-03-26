package com.example.android2.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android2.mvp.model.entity.room.RoomGithubRepository
import com.example.android2.mvp.model.entity.room.RoomGithubUser
import com.example.android2.mvp.model.entity.room.dao.RepositoryDao
import com.example.android2.mvp.model.entity.room.dao.UserDao

@androidx.room.Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance ?: throw IllegalStateException("Database has not been created")
        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
            }
        }
    }
}