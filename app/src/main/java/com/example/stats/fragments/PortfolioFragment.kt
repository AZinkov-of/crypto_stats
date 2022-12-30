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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myenglishapp.SwipeToDelete
import com.example.stats.*
import com.example.stats.databinding.FragmentPortfolioBinding
import com.example.stats.viewmodel.AssetsListViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var adapter: PortfolioAdapter
    private var sum = 0.0

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

        binding.addTransaction.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragment, NewTransactionFragment.newInstance()).commit()
        }

//        binding.deleteItem.setOnClickListener {
//            GlobalScope.launch {
//                val ass = viewModel.getFullCost()
//                if (ass.isEmpty()) {
//                    addAsset()
//                } else {
//                    val i = (ass.indices).random()
//                    viewModel.deleteAsset(ass[i])
//                }
//            }
//
//            val job: Job = GlobalScope.launch {
//                val ass1 = viewModel.getFullCost()
//                sum = ass1.sumOf { it.price * it.volume }
//            }
////            job.invokeOnCompletion {
////                binding.fullCost.text = "$"+sum.toString()
////            }
//        }
        addAsset()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        val onItemSwipedToDelete = { positionForRemove: Int ->
            Thread(Runnable {
                val assets= viewModel.getAssets()
                viewModel.deleteAsset(assets[positionForRemove])
            }).start()
        }

        val swipeToDeleteCallback = SwipeToDelete(onItemSwipedToDelete)
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.portfolioRecivlerView)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PortfolioFragment()
    }


    fun addAsset() {
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
    }
}