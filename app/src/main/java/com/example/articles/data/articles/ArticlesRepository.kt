package com.example.articles.data.articles

import com.example.articles.dagger.FeatureScope
import com.example.articles.data.Result
import com.example.articles.data.model.ArticleUIModel
import com.example.articles.data.model.Articles
import com.example.articles.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FeatureScope
class ArticlesRepository @Inject constructor(
    private val dataSource: ArticlesRemoteDataSource,
    private val localDataSource: ArticlesLocalDataSource,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {


    suspend fun getArticles(): Result<MutableList<Articles>> {

        return dataSource.getArticles()

    }

    private suspend fun compareWithSavedData(remoteArticles: Result.Success<MutableList<Articles>>) {
        val articles = remoteArticles.data.map {
            ArticleUIModel(
                imageUri = it.media[0].uri,
                likeCounter = 0,
                title = it.title,
                onLikeBtnClicked = null,
                onDisLikeBtnClicked = null
            )
        }.toMutableList()

        val savedArticles:List<ArticleUIModel> = withContext(dispatcherProvider.io){
            localDataSource.getArticles()
        }

       val d =  articles.zip(savedArticles).filter { it.first.title == it.second.title }
           .map {
               ArticleUIModel(
                   imageUri = it.second.imageUri,
                   title = it.second.title,
                   likeCounter =  it.second.likeCounter,
                   onDisLikeBtnClicked = null ,
                   onLikeBtnClicked = null
               )
       }


        




    }

    suspend fun saveArticle(article: ArticleUIModel) {
        localDataSource.insertArticle(article)

    }

    suspend fun getLocalArticles():  List<ArticleUIModel> {
        return localDataSource.getArticles()

    }

    suspend fun clearData() {
        localDataSource.clear()

    }


}