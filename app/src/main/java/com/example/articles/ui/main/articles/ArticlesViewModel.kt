package com.example.articles.ui.main.articles

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.util.CoroutinesDispatcherProvider

class ArticlesViewModel(
    private val articlesRepository: ArticlesRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    private val context: Context
) : ViewModel() {

     fun start(){

    }
}
