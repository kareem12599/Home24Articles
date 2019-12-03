package com.example.articles.data.model

data class ArticleUIModel(
    var imageUri: String?,
    var likeCounter: Int,
    val title: String,
    var onLikeBtnClicked: ((postion: Int, article: ArticleUIModel) -> Unit)?,
    var onDisLikeBtnClicked: ((postion: Int, article: ArticleUIModel) -> Unit)?

)