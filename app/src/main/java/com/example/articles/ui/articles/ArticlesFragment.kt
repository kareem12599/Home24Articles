package com.example.articles.ui.articles

import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.articles.R
import com.example.articles.data.model.ArticleUIModel
import com.example.articles.util.inject
import com.example.articles.util.setUpActionBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.articles_fragment.*
import java.util.ArrayList
import javax.inject.Inject

class ArticlesFragment : Fragment(), ViewPager.OnPageChangeListener {


    @Inject
    lateinit var viewModel: ArticlesViewModel
    lateinit var articles: MutableList<ArticleUIModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.articles_fragment, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {

        inject(this)
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setUpActionBar("Articles")
        setupViewModelObservers()
        loadViewData()


        reviewButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_articlesFragment_to_reviewFragment)
        }
    }

    private fun loadViewData() {
        viewModel.start()
        progress.visibility = VISIBLE

    }

    private fun setupViewModelObservers() {
        viewModel.articles.observe(this, Observer { articles ->
            progress.visibility = View.GONE
            if (!articles.isNullOrEmpty()) {
                if (articlesViewPager.adapter == null) initViewPager(articles.toMutableList())
                this.articles = articles.toMutableList()
                reviewButton.isEnabled =
                    articles.filter { it.likeCounter > 0 }.size == articles.size
            } else {
                Snackbar.make(main, "Error in fetching data", Snackbar.LENGTH_INDEFINITE).show()
            }
        })
        viewModel.count.observe(this, Observer { count ->
            articleReview.visibility = VISIBLE
            articleReview.text = "$count/10"

        })
        viewModel.errorMessage.observe(this, Observer { message ->
            errorMessage.visibility = VISIBLE
            errorMessage.text = message
        })

    }


    private fun initViewPager(articles: MutableList<ArticleUIModel?>?) {



        articles?.add(articles.size, null )

        articlesViewPager.offscreenPageLimit = 0
        val adapter =
            ArticlesPagerAdapter(this, articles as ArrayList<ArticleUIModel>, articlesViewPager)
        articlesViewPager.adapter = adapter
        articlesViewPager.removeOnPageChangeListener(this)
        articlesViewPager.addOnPageChangeListener(this)
        dotIndicator.setupWithViewPager(articlesViewPager)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (!articles.isNullOrEmpty() && position < articles.size)

            viewModel.updateCount(position)
        else
            articleReview.visibility = INVISIBLE

    }

    override fun onPageSelected(position: Int) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}

