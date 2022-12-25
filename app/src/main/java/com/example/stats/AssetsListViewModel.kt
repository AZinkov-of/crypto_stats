package com.example.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssetsListViewModel(application: Application)
    : AndroidViewModel(application) {

    val assets: LiveData<List<Asset>>

    init{
        val userDao_ = ADatabase.getDatabase(application).userDao()
        assets = userDao_.getAssets()
        loadAssets()
    }

    override fun onCleared() {
        super.onCleared()
//        assetService.removeListener(listener)
    }

    private fun loadAssets() {
//        assetService.addListener(listener)
    }

    fun deleteAssets(asset: Asset) {
//        assetService.deleteAsset(asset)
    }

//    private val listener:AssetListener = {
////        _assets.value = it
//    }
}