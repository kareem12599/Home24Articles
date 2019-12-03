package com.example.articles.dagger

import com.example.articles.MainActivity
import com.example.articles.ui.articles.ArticlesFragment
import com.example.articles.ui.review.ReviewFragment
import com.example.articles.ui.start.StartFragment
import dagger.Subcomponent


@Subcomponent(modules = [ArticlesModule::class])
@FeatureScope
interface ArticleComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ArticleComponent
        fun articleModule(module: ArticlesModule): Builder
    }

    fun inject(mainActivity: MainActivity)
    fun inject(articleFragment: ArticlesFragment)
    fun inject(startFragment: StartFragment)
    fun inject(reviewFragment: ReviewFragment)
}