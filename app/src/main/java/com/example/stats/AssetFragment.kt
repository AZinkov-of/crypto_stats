package com.example.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stats.databinding.FragmentAssetBinding

class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding

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
            val price = bundle.getDouble("price")

            binding.asset.text = ticker
            binding.Price.text = "$$price"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}