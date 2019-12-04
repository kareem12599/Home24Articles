package com.example.articles.ui.review

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.articles.R
import com.example.articles.data.model.ArticleUIModel
import com.example.articles.ui.articles.ArticlesViewModel
import com.example.articles.util.CoroutinesDispatcherProvider
import com.example.articles.util.inject
import com.example.articles.util.setUpActionBar
import kotlinx.android.synthetic.main.review_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReviewFragment : Fragment() {

    @Inject
    lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticlesReviewListAdapter
    private var articles: MutableList<ArticleUIModel>? = null
    @Inject
    lateinit var dispatcherProvider: CoroutinesDispatcherProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        inject(this)
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setUpActionBar("Reviews")
        initListeners()
        setUpAdapter()

       lifecycleScope.launch(dispatcherProvider.main){
           progress.visibility = View.VISIBLE
           delay(500)
           viewModel.start()
       }



        viewModel.articles.observe(this, Observer { articles ->
            progress.visibility = View.GONE
            this.articles = articles?.toMutableList()
            adapter.submitList(articles)

        })


    }

    private fun setUpAdapter() {
        adapter = ArticlesReviewListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun initListeners() {



    }

    private fun submitListWithoutTitle() {
        adapter.submitList(articles?.map {
            ArticleUIModel(
                imageUri = it.imageUri,
                title = "",
                likeCounter = it.likeCounter,
                onLikeBtnClicked = { _, _ -> },
                onDisLikeBtnClicked = { _, _ -> }

            )
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
            R.id.view_type->  {
                if (recyclerView.layoutManager !is GridLayoutManager) {
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                    submitListWithoutTitle()

                } else {
                    recyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter.submitList(articles)
                    adapter.notifyDataSetChanged()
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
