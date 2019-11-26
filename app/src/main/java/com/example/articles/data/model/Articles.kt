package com.example.articles.data.model

data class Articles (

    val sku : Int,
    val title : String,
    val description : String,
    val prevPrice : String,
    val manufacturePrice : String,
    val price : Price,
    val media : List<Media>,
    val assemblyService : String,
    val availability : String,
    val url : String,
    val energyClass : String,
    val discount : Int,
    val reviews : Reviews,
    val availableForAR : Boolean,
    val labels : List<Labels>
)