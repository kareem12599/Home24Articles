package com.example.articles.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ArticleUIModel(
    var imageUri: String?,
    var likeCounter: Int,
    val title: String,
    @Ignore
    var onLikeBtnClicked: ((postion: Int, article: ArticleUIModel) -> Unit)?,
    @Ignore
    var onDisLikeBtnClicked: ((postion: Int, article: ArticleUIModel) -> Unit)?

){
    @PrimaryKey(autoGenerate = true)
    var _id:Int = 0
}