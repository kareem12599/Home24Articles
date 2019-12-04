package com.example.articles.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.articles.data.model.ArticleUIModel

@Database(entities = [ArticleUIModel::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {

        private const val DATABASE_NAME = "articles-db"

        // For Singleton instantiation
        @Volatile private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context, ArticleDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}