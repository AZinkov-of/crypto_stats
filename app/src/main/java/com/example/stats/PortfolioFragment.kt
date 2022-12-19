package com.example.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stats.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var adapter: PortfolioAdapter
    private val viewModel: AssetsListViewModel by viewModels { factory() }

    private val assetService = AssetService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PortfolioAdapter(object : ActionListener {
//            override fun deleteAsset(asset: Asset) {
//                viewModel.deleteAssets(asset)
//            }
        })

//        viewModel.assets.observe(viewLifecycleOwner, Observer {
//            adapter.potfolioList = it
//        })

        binding.portfolioRecivlerView.adapter = adapter
        binding.portfolioRecivlerView.layoutManager = LinearLayoutManager(binding.root.context)

        var ass = AssetService()
        adapter.setData(ass.getAssets())
    }

    companion object {
        @JvmStatic
        fun newInstance() = PortfolioFragment()
    }
}