package com.example.articles.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ArticleUIModel(
    var imageUri: String?,
    var likeCounter: Int,
    var title: String?,
    @Ignore
    var onLikeBtnClicked: ((position: Int, article: ArticleUIModel) -> Unit)?,
    @Ignore
    var onDisLikeBtnClicked: ((position: Int, article: ArticleUIModel) -> Unit)?

){
    @PrimaryKey(autoGenerate = true)
    var _id:Int = 0
    constructor():this(null, 0 , null, null, null)
}