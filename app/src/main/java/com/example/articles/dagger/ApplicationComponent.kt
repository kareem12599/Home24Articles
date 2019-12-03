package com.example.articles.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubComponentsModule::class])
interface ApplicationComponent {
    fun articlesComponentBuilder():ArticleComponent.Builder
}