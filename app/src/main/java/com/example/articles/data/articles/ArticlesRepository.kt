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
        val articles = dataSource.getArticles()
        if (articles is Result.Success)
            compareWithSavedData(articles)



        return articles

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
        




    }


}