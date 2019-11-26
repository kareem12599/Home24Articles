package com.example.articles.data.articles

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepository @Inject constructor(private val dataSource: ArticlesRemoteDataSource) {

}