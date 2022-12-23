package com.example.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            override fun changeAsset(asset: Asset) {
//                binding.textView3.text = "sdsdfsdf" + (0..100).random().toString()
//                viewModel.deleteAssets(asset)
                val assfragm = AssetFragment.newInstance()
                val bundle = Bundle()
                bundle.putString("ticker", asset.ticker)
                bundle.putDouble("price", asset.price)
                // TODO: переписать по человечески 
                assfragm.arguments = bundle
                    parentFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.fragment,assfragm ).commit()
//                val par = parentFragmentManager.getFragment()
//                if (par != null) {
//                    parentFragmentManager.beginTransaction().addToBackStack(null)
//                        .replace(par, AssetFragment.newInstance()).commit()
//                }
            }
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