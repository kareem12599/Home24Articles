package com.example.articles.util

import androidx.fragment.app.Fragment
import com.example.articles.MainActivity
import com.example.articles.dagger.ArticlesModule
import com.example.articles.getAppComponent
import com.example.articles.ui.articles.ArticlesFragment
import com.example.articles.ui.review.ReviewFragment
import com.example.articles.ui.start.StartFragment

fun inject(fragment: Fragment) {
    val component = fragment.requireActivity().getAppComponent().articlesComponentBuilder()
        .articleModule(ArticlesModule(fragment)).build()

    when (fragment) {
        is ArticlesFragment -> component.inject(articleFragment = fragment)
        is StartFragment -> component.inject(startFragment = fragment)
        is ReviewFragment -> component.inject(reviewFragment = fragment)
        else -> component.inject(mainActivity = fragment.requireActivity() as MainActivity)
    }

}