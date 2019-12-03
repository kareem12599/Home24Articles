package com.example.articles.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE

import android.view.ViewGroup

import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.articles.R
import com.example.articles.data.model.ArticleUIModel

import kotlinx.android.synthetic.main.article_item.view.*
import java.util.*

class ArticlesPagerAdapter : PagerAdapter {
    private var viewPager: ViewPager
    private var context: Context? = null
    private var fragment: ArticlesFragment
    private var articles: ArrayList<ArticleUIModel>

    constructor(
        fragment: ArticlesFragment,
        articles: ArrayList<ArticleUIModel>,
        articlePager: ViewPager
    ) {
        this.articles = articles
        context = fragment.context
        this.fragment = fragment
        this.viewPager = articlePager
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context)
            .inflate(R.layout.article_item, container, false)

        mapViewData(view, articles[position], position)
        container.addView(view)
        return view
    }


    private fun mapViewData(articleView: View, article: ArticleUIModel?, position: Int) {
        if (article == null) {
            handleNomoreArticle(articleView)
            return
        }

        if (!article.imageUri.isNullOrEmpty())
            context?.let { Glide.with(it).load(article.imageUri).into(articleView.articleImage) }


        articleView.likeButton.setOnClickListener {
            article.apply {
                if (likeCounter in 0..9) ++likeCounter
                onLikeBtnClicked?.let { it1 -> it1(position, article) }
                if (viewPager.currentItem < count - 1)
                    viewPager.currentItem = viewPager.currentItem + 1
            }
        }
        articleView.dislikeButton.setOnClickListener {
            article.apply {
                if (likeCounter in 1..10) --likeCounter
                onDisLikeBtnClicked?.let { it1 -> it1(position, article) }
                if (viewPager.currentItem < count - 1)
                    viewPager.currentItem = viewPager.currentItem + 1

            }
        }
    }

    private fun handleNomoreArticle(articleView: View) {
        with(articleView) {
            articleCard.visibility = GONE
            title.text = "There is no more images"
        }

    }

    override fun getCount(): Int {
        return articles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}