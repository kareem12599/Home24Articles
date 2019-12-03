package com.example.articles.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.articles.data.model.ArticleUIModel

@Dao
interface ArticleDao {
    @Query("select * from articleuimodel ")
    suspend fun getArticles():List<ArticleUIModel>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleUIModel: ArticleUIModel)
}