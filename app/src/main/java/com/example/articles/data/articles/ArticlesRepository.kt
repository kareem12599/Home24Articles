package com.example.articles.data.articles

import com.example.articles.dagger.FeatureScope
import com.example.articles.data.Result
import com.example.articles.data.model.Articles
import javax.inject.Inject

@FeatureScope
class ArticlesRepository @Inject constructor(private val dataSource: ArticlesRemoteDataSource, 
                                             private val localDataSource: ArticlesLocalDataSource) {

    suspend fun getArticles():Result<MutableList<Articles>>{
        val articles =  dataSource.getArticles()
        if (articles is Result.Success)
            compareWithSavedData(articles)
        
        
        
        return articles
        
    }

    private fun compareWithSavedData(articles: Result.Success<MutableList<Articles>>) {

    }


}