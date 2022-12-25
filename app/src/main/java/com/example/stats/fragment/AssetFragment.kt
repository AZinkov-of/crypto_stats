package com.example.stats.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stats.AssetsListViewModel
import com.example.stats.databinding.FragmentAssetBinding

class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding
    lateinit var viewModel: AssetsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            val ticker = bundle.getString("ticker")

            binding.asset.text = ticker
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
            ).get(AssetsListViewModel::class.java)

//            binding.Price.text = "$$price"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}