package com.example.articles.data.api

import com.example.articles.data.model.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesService {
    @GET("articles")
    suspend fun getArticles(
        @Query("appDomain") appDomain: Long = 1,
        @Query("locale") locale: String = "de_DE",
        @Query("limit") limit: Long = 10
    ):Response<Articles>
    companion object{
     const val ENDPOINT  = "https://api-mobile.home24.com/api/v2.0/categories/100/"
    }
}
//https://api-mobile.home24.com/api/v2.0/categories/100/articles?appDomain=1&locale=de_DE&limit=10