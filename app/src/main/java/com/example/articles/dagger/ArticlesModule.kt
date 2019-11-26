package com.example.articles.dagger

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.articles.data.articles.ArticlesRepository
import com.example.articles.ui.main.articles.ArticlesFragment
import com.example.articles.ui.main.articles.ArticlesViewModel
import com.example.articles.util.ArticlesViewModelFactory
import com.example.articles.util.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides


@Module(subcomponents = [ArticleComponent::class])
class ArticlesModule(private  val fragment: ArticlesFragment) {
    @FeatureScope
    @Provides
    fun provideArticlesViewModelFactory(articlesRepository: ArticlesRepository,
                                        dispatcherProvider: CoroutinesDispatcherProvider,
                                        context: Context
    ):ArticlesViewModelFactory{
        return ArticlesViewModelFactory(articlesRepository, dispatcherProvider, context)
    }
    @FeatureScope

    @Provides
    fun provideArticlesViewModel(articlesViewModelFactory: ArticlesViewModelFactory)
            :ArticlesViewModel= ViewModelProviders.of(fragment, articlesViewModelFactory).get(ArticlesViewModel::class.java)

}