package com.example.stats

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       val viewModel = when(modelClass) {
           AssetsListViewModel::class.java -> {
               AssetsListViewModel(app.assetService)
           }
           else -> {
               throw IllegalStateException("Unknown view model class")
           }
       }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)
