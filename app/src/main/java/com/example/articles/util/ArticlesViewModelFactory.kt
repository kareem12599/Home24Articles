package com.example.articles.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.ui.articles.ArticlesViewModel
import java.lang.IllegalArgumentException

class ArticlesViewModelFactory(
    private val articlesRepository: ArticlesRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != ArticlesViewModel::class.java) {
            throw IllegalArgumentException("Unknown viewmodel class")
        }
        return ArticlesViewModel(articlesRepository, dispatcherProvider) as T
    }
}