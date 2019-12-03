package com.example.articles.ui.articles

import android.net.Uri
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.articles.data.Result
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.data.model.ArticleUIModel
import com.example.articles.data.model.Articles
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val articlesRepository: ArticlesRepository
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


    fun start() = launchDataLoad {
        articlesRepository.getArticles()
    }

    private fun launchDataLoad(block: suspend () -> Result<List<Articles>>): Unit {
        viewModelScope.launch {

            when (val result = block()) {
                is Result.Success -> {
                    val result = result.data
                    updateSourceUIModel(result)
                }
                is Result.Error -> {
                    _articles.value = emptyList()
                    _errorMessage.value = result.toString()
                }
            }

        }
    }


    private fun updateSourceUIModel(result: List<Articles>) {
        val articles = result.toMutableList()
        _articles.value = articles.map {
            ArticleUIModel(imageUri = it.media[0].uri,
                likeCounter = 0,
                title = it.title,
                onLikeBtnClicked = { position, article -> setCount(position, article) },
                onDisLikeBtnClicked = { position, article -> resetCount(position, article) }
            )
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
}

