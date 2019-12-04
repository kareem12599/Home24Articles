package com.example.articles.data.articles

import com.example.articles.data.database.ArticleDao
import com.example.articles.data.model.ArticleUIModel
import javax.inject.Inject


class ArticlesLocalDataSource @Inject constructor(val dao: ArticleDao){
     suspend fun getArticles(): List<ArticleUIModel> {
       return  dao.getArticles()
    }

    suspend fun insertArticle(articleUIModel: ArticleUIModel)=
        dao.insert(articleUIModel)

    suspend fun clear() {
        dao.deleteAll()
    }


}