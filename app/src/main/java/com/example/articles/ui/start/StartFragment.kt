package com.example.articles.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.articles.R
import com.example.articles.ui.articles.ArticlesViewModel
import com.example.articles.util.ConnectivityChecker
import com.example.articles.util.inject
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class StartFragment : Fragment() {
    @Inject
    lateinit var viewModel: ArticlesViewModel

    @Inject
     lateinit var connectivityChecker: ConnectivityChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        connectivityChecker.apply {
            lifecycle.addObserver(this)
            connectedStatus.observe(this@StartFragment , Observer<Boolean> {
                if (it) {
                    handleNetworkConnected()
                } else {
                    handleNoNetworkConnection()
                }
            })
        }



    }

    private fun handleNoNetworkConnection() {
        startBtn.setOnClickListener {
            Snackbar.make(main, "Internet disconnected", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun handleNetworkConnected() {
        startBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_startFragment_to_articlesFragment)

        }

    }

}
