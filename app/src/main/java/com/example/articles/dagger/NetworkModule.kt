package com.example.articles.dagger

import android.app.Activity
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.example.articles.data.api.ArticlesService
import com.example.articles.util.ConnectivityChecker
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideArticleService():ArticlesService =
        Retrofit.Builder()
            .baseUrl(ArticlesService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticlesService::class.java)
}