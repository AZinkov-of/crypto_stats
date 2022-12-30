package com.example.stats.fragments

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.stats.Asset
import com.example.stats.R
import com.example.stats.databinding.FragmentNewTransactionBinding
import com.example.stats.databinding.FragmentPortfolioBinding
import com.example.stats.viewmodel.AssetsListViewModel
import kotlin.math.roundToInt

class NewTransactionFragment : Fragment() {
    private lateinit var binding: FragmentNewTransactionBinding
    private var flag = true
    lateinit var viewModel: AssetsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTransactionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireContext().applicationContext as Application)
        ).get(AssetsListViewModel::class.java)

        binding.amount.addTextChangedListener {
            if (binding.price.text.isNotEmpty() and flag and binding.amount.text.isNotEmpty()) {
                flag = false
                val a = binding.amount.text.toString().toDouble()
                val b = binding.price.text.toString().toDouble()
                val res = ((a * b) * 1000).roundToInt() / 1000.0
                binding.all.setText(res.toString())
                flag = true
            }
        }

        binding.price.addTextChangedListener {
            if (binding.amount.text.isNotEmpty() and flag and binding.price.text.isNotEmpty()) {
                flag = false
                val a = binding.amount.text.toString().toDouble()
                val b = binding.price.text.toString().toDouble()
                val res = ((a * b) * 1000).roundToInt() / 1000.0
                binding.all.setText(res.toString())
                flag = true
            }
        }

        binding.all.addTextChangedListener {
            if (binding.price.text.isNotEmpty() and flag and binding.all.text.isNotEmpty()) {
                flag = false
                val a = binding.all.text.toString().toDouble()
                val b = binding.price.text.toString().toDouble()
                val res = ((a / b) * 1000).roundToInt() / 1000.0
                binding.amount.setText(res.toString())
                flag = true
            }
        }
        binding.addTransaction.setOnClickListener {
            val price = binding.price.text
            val ticker = binding.ticker.text
            val amount = binding.amount.text
            val all = binding.all.text

            if (price.isNotEmpty() and all.isNotEmpty() and
                ticker.isNotEmpty() and amount.isNotEmpty()
            ) {
                viewModel.addAsset(
                    Asset(
                        ticker.toString(),
                        amount.toString().toDouble(),
                        price.toString().toDouble()
                    )
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, PortfolioFragment.newInstance()).commit()

            } else {
                Toast.makeText(
                    requireContext().applicationContext,
                    "not all fields are filled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewTransactionFragment()
    }
}