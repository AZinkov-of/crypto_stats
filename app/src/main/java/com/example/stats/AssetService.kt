package com.example.stats

typealias AssetListener = (assets: List<Asset>) -> Unit

class AssetService {
    private var assets = mutableListOf<Asset>()
    private val listeners = mutableSetOf<AssetListener>()
    private var loaded = false

    init {
        tickers.shuffle()
        assets = (0..19).map {
            Asset(
                tickers[it],
                (0..10000).random().toDouble() / 1000,
                (0..10000000).random().toDouble() / 10000
            )
        }.toMutableList()
    }

    fun getAssets(): List<Asset> {
        return assets
    }

    fun deleteAsset(asset: Asset) {
        val index = assets.indexOfFirst { it.ticker == asset.ticker }
        if (index != -1) {
            assets.removeAt(index)
            notifyChanges()
        }
    }

    fun addListener(listener: AssetListener) {
        listeners.add(listener)
        if (loaded) {
            listener.invoke(assets)
        }
    }

    fun removeListener(listener: AssetListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        if (!loaded) return
        listeners.forEach { it.invoke(assets) }
    }

    companion object {
        private val tickers = mutableListOf<String>(
            "ETH",
            "BTC",
            "USDT",
            "BNB",
            "ADA",
            "SOL",
            "DOGE",
            "DOT",
            "MATIC",
            "TRX",
            "AVAX",
            "ATOM",
            "UNI",
            "ETC",
            "LTC",
            "FTT",
            "NEAR",
            "XLM",
            "XMR",
            "FLOW"
        )
    }
}
