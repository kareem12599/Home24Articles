package com.example.articles.ui.articles

import android.net.Uri
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.articles.data.Result
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.data.model.ArticleUIModel
import com.example.articles.data.model.Articles
import com.example.articles.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesViewModel(
    private val articlesRepository: ArticlesRepository,
    private val dispatcher: CoroutinesDispatcherProvider
) : ViewModel() {


    private val _articles = MutableLiveData<List<ArticleUIModel>?>()
    val articles: LiveData<List<ArticleUIModel>?>
        get() = _articles

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int>
        get() = _count

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage


    fun start() = launchDataLoad({ articlesRepository.getArticles() },
        { articlesRepository.getLocalArticles() })


    private fun launchDataLoad(
        block1: suspend () -> Result<List<Articles>>,
        block2: suspend () -> List<ArticleUIModel>
    ): Unit {
        viewModelScope.launch(dispatcher.io) {
            var savedArticles = block2()
            when (val result = block1()) {
                is Result.Success -> {
                    val result = result.data
                    updateSourceUIModel(result, savedArticles)
                }
                is Result.Error -> {
                    viewModelScope.launch(dispatcher.main) {
                        _articles.value = emptyList()
                        _errorMessage.value = result.toString()
                    }
                }
            }

        }
    }


    private fun updateSourceUIModel(result: List<Articles>, localData: List<ArticleUIModel>) {

        viewModelScope.launch(dispatcher.main) {

            when {
                localData.isNotEmpty() && localData.size == result.size ->
                    _articles.value = result.zip(localData)
                        .filter { it.first.title == it.second.title }
                        .map {
                            ArticleUIModel(
                                imageUri = it.first.media[0].uri,
                                title = it.first.title,
                                likeCounter = it.second.likeCounter,
                                onLikeBtnClicked = { position, article ->
                                    setCount(
                                        position,
                                        article
                                    )
                                },
                                onDisLikeBtnClicked = { position, article ->
                                    resetCount(
                                        position,
                                        article
                                    )
                                }

                            )
                        }
                else -> _articles.value = result.map {
                    ArticleUIModel(
                        imageUri = it.media[0].uri,
                        title = it.title,
                        likeCounter = 0,
                        onLikeBtnClicked = { position, article -> setCount(position, article) },
                        onDisLikeBtnClicked = { position, article -> resetCount(position, article) }

                    )
                }
            }


        }
    }


    private fun resetCount(position: Int, article: ArticleUIModel) {
        _count.value = article.likeCounter
        updateLiveData(position, article)

    }

    private fun setCount(position: Int, article: ArticleUIModel) {
        _count.value = article.likeCounter
        updateLiveData(position, article)
    }

    private fun updateLiveData(position: Int, article: ArticleUIModel) {
        val articles = _articles.value?.toMutableList()
        articles?.apply { set(position, article) }
        _articles.value = articles


    }


    fun updateCount(position: Int) {
        _count.value = articles.value?.get(position)?.let { it.likeCounter }
    }

    fun saveArticles(articles: MutableList<ArticleUIModel>) {
        viewModelScope.launch(dispatcher.io) {
            articlesRepository.clearData()
            with(articles) {

                forEach { article ->

                    articlesRepository.saveArticle(article)
                }
            }
        }

    }
}

