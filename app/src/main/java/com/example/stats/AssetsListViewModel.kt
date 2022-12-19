package com.example.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssetsListViewModel(private val assetService: AssetService) : ViewModel() {
    private val _assets = MutableLiveData<List<Asset>>()
    val assets: LiveData<List<Asset>> = _assets

    init{
        loadAssets()
    }

    override fun onCleared() {
        super.onCleared()
        assetService.removeListener(listener)
    }

    private fun loadAssets() {
        assetService.addListener(listener)
    }

    fun deleteAssets(asset: Asset) {
//        assetService.deleteAsset(asset)
    }

    fun addTransactions() {

    }

    private val listener:AssetListener = {
//        _assets.value = it
    }
}