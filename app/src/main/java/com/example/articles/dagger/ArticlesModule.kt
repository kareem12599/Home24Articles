package com.example.articles.dagger

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.data.database.ArticleDao
import com.example.articles.data.database.ArticleDatabase
import com.example.articles.ui.articles.ArticlesViewModel
import com.example.articles.util.ArticlesViewModelFactory
import com.example.articles.util.ConnectivityChecker
import dagger.Module
import dagger.Provides


@Module
class ArticlesModule(private val fragment: Fragment) {
    @FeatureScope
    @Provides
    fun provideArticlesViewModelFactory(
        articlesRepository: ArticlesRepository
    ): ArticlesViewModelFactory {
        return ArticlesViewModelFactory(articlesRepository)
    }
    @Provides
    fun provideContext(): Context = fragment.context!!


    @FeatureScope
    @Provides
    fun provideArticlesViewModel(articlesViewModelFactory: ArticlesViewModelFactory)
            : ArticlesViewModel =
        ViewModelProviders.of(fragment, articlesViewModelFactory).get(ArticlesViewModel::class.java)

    @FeatureScope
    @Provides
    fun connectivityChecker(): ConnectivityChecker {
        val connectivityManager = fragment.requireActivity().getSystemService<ConnectivityManager>()
        return ConnectivityChecker(connectivityManager!!)
    }

    @Provides
    @FeatureScope
    fun provideLoggedInUserDao(context: Context): ArticleDao {
        return ArticleDatabase.getInstance(context).articleDao()
    }

}