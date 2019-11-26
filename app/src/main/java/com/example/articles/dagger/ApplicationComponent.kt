package com.example.articles.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ArticlesModule::class])
interface ApplicationComponent {
    fun articlesComponent():ArticleComponent.Factory
}