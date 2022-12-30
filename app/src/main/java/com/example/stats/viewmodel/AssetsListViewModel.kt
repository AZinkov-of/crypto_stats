package com.example.stats.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.stats.Asset
import com.example.stats.model.ADatabase
import com.example.stats.model.dao.AssetDao
import kotlinx.coroutines.*

class AssetsListViewModel(application: Application) : AndroidViewModel(application) {
    val assets: LiveData<List<Asset>>
    private lateinit var assetDao_: AssetDao

    init {
        assetDao_ = ADatabase.getDatabase(application).assetDao()
        assets = assetDao_.getAssets()
    }

    fun addAsset(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) {
            assetDao_.addAsset(asset)
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) {
            assetDao_.deleteAsset(asset)
        }
    }

    fun getAssets ():List<Asset> {
        return  assetDao_.getAssetsN()
    }
}