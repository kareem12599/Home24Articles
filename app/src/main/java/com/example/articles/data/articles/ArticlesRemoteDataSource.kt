package com.example.articles.data.articles

import com.example.articles.data.Result
import com.example.articles.data.api.ArticlesService
import com.example.articles.data.model.Articles
import com.example.articles.util.safeApiCall
import java.io.IOException
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(private val service: ArticlesService) {
    suspend fun getArticles() = safeApiCall(
        call = {requestGetArticles()},
        errorMessage = "Error getting articles"
    )

    private suspend fun requestGetArticles(): Result<Articles>{
        val response  = service.getArticles()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }

        return Result.Error(
            IOException("Error getting articles ${response.code()} ${response.message()}")
        )

    }
}