package com.example.stats.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stats.*
import com.example.stats.databinding.FragmentPortfolioBinding
import com.example.stats.viewmodel.AssetsListViewModel

class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var adapter: PortfolioAdapter

    lateinit var viewModel: AssetsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
        ).get(AssetsListViewModel::class.java)

        adapter = PortfolioAdapter(object : ActionListener {
            @SuppressLint("SuspiciousIndentation")
            override fun viewAsset(asset: Asset) {
                val assfragm = AssetFragment.newInstance()
                val bundle = Bundle()
                bundle.putString("ticker", asset.ticker)

                assfragm.arguments = bundle
                parentFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.fragment, assfragm).commit()
            }
        })

        viewModel.assets.observe(viewLifecycleOwner, Observer {
            adapter.potfolioList = it
        })

        binding.portfolioRecivlerView.adapter = adapter
        binding.portfolioRecivlerView.layoutManager = LinearLayoutManager(binding.root.context)

        val tickers = mutableListOf<String>(
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

        var assets = mutableListOf<Asset>()
        tickers.shuffle()
        assets = (0..19).map {
            Asset(
                tickers[it],
                (0..10000).random().toDouble() / 1000,
                (0..10000000).random().toDouble() / 10000
            )
        }.toMutableList()

        assets.forEach {
            viewModel.addAsset(it)
        }

//        viewModel.addAsset(Asset("sdf",12.2,23.1))
    }

    companion object {
        @JvmStatic
        fun newInstance() = PortfolioFragment()
    }
}