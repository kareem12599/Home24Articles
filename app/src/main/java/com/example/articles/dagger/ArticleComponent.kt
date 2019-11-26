package com.example.articles.dagger

import com.example.articles.MainActivity
import com.example.articles.ui.main.articles.ArticlesFragment
import com.example.articles.ui.main.start.StartFragment
import dagger.Component
import dagger.Subcomponent

@FeatureScope
@Subcomponent
interface ArticleComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ArticleComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(articleFragment: ArticlesFragment)
    fun inject(startFragment: StartFragment)
}