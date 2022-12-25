package com.example.stats

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssetsListViewModel(application: Application)
    : AndroidViewModel(application) {

    val assets: LiveData<List<Asset>>
    val assetDao_ = ADatabase.getDatabase(application).assetDao()

    init{
        val assetDao_ = ADatabase.getDatabase(application).assetDao()
        assets = assetDao_.getAssets()
    }

    fun addAsset(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) {
            assetDao_.addUser(asset)
        }
    }
    

//
//    override fun onCleared() {
//        super.onCleared()
////        assetService.removeListener(listener)
//    }
//
//    private fun loadAssets() {
////        assetService.addListener(listener)
//    }
//
//    fun deleteAssets(asset: Asset) {
////        assetService.deleteAsset(asset)
//    }
//
////    private val listener:AssetListener = {
//////        _assets.value = it
////    }
}