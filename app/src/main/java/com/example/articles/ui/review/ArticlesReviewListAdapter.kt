package com.example.articles.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.articles.R
import com.example.articles.data.model.ArticleUIModel
import kotlinx.android.synthetic.main.article_list_item.view.*

class ArticlesReviewListAdapter : ListAdapter<ArticleUIModel, ArticlesViewHolder>(DiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
       holder.bindTo(getItem(position))
    }

    private class DiffCallBack : DiffUtil.ItemCallback<ArticleUIModel>() {
        override fun areItemsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
            return oldItem.title == newItem.title;
        }

        override fun areContentsTheSame(oldItem: ArticleUIModel, newItem: ArticleUIModel): Boolean {
          return oldItem == newItem
        }

    }
}


class ArticlesViewHolder(private  val rootView: View) : RecyclerView.ViewHolder(rootView) {
    fun bindTo(item: ArticleUIModel?) {
        item?.let {
            Glide.with(rootView.context).load(it.imageUri).into(rootView.articleReviewImage)
            if (!item.title.isNullOrEmpty())
                rootView.articleTitle.text = it.title
             else
                rootView.articleTitle.visibility = INVISIBLE
        }

    }

}

