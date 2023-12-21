package com.example.stats.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stats.viewmodel.AssetsListViewModel
import com.example.stats.databinding.FragmentAssetBinding
import com.example.stats.model.crypto_cost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding
    lateinit var viewModel: AssetsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            val ticker = bundle.getString("ticker")

            binding.asset.text = ticker
            val df = DecimalFormat("#.##")
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
            ).get(AssetsListViewModel::class.java)

            GlobalScope.launch {
                var asset= viewModel.getAssetToTicker(ticker?:"_")
                if (asset.isNotEmpty()) {

                    binding.Price.setText(df.format( crypto_cost.getOrDefault(asset[0].ticker,0)).toString() +" $")
                    binding.asset.setText(asset[0].ticker.toString())
                    binding.vol.setText(df.format(asset[0].quantity).toString() +"  "+ asset[0].ticker.toString())
//                    binding.cost.setText(df.format(asset[0].quantity *  crypto_cost.getOrDefault(asset[0].ticker,0)).toString() + "  $")
                }
            }

//            binding.Price.text = "$$price"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}