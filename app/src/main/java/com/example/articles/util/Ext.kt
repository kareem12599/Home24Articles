package com.example.articles.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setUpActionBar(actionbarTitle:String){
    (requireActivity() as AppCompatActivity).supportActionBar?.apply {
        show()
        setDisplayHomeAsUpEnabled(true)
        title = actionbarTitle

    }
}