package com.example.stats

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stats.databinding.PortfolioItemBinding
import java.text.DecimalFormat

interface ActionListener {
    fun viewAsset(asset: Asset)
}

class PortfolioDiffCallback(private val oldList: List<Asset>, private val  newList: List<Asset>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].ticker == newList[newItemPosition].ticker
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition]==newList[newItemPosition]
    }
}

class PortfolioAdapter(private val actionListener: ActionListener) :
    RecyclerView.Adapter<PortfolioAdapter.MyViewHolder>(), View.OnClickListener {
    var potfolioList: List<Asset> = emptyList()
        set(newValue) {
            val difUpdate =  DiffUtil.calculateDiff(PortfolioDiffCallback(field,newValue))
            field = newValue
            difUpdate.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PortfolioItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.price.setOnClickListener(this)
        return MyViewHolder(binding)
    }

    class MyViewHolder(
        val binding: PortfolioItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val ass = v.tag as Asset
        when (v.id) {
            R.id.price -> {
                actionListener.viewAsset(ass)
            }
            else -> {
                actionListener.viewAsset(ass)
            }
        }
    }

    override fun getItemCount(): Int {
        return potfolioList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val df = DecimalFormat("#.##")
        val per = ((0..2000).random().toDouble() / 100) - 10
        val currentItem = potfolioList[position]

        with(holder.binding) {
            Ticker.text = currentItem.ticker
            volume.text = df.format(currentItem.volume).toString()
            price.text = "$" + df.format(currentItem.price).toString()
            cost.text = "$" + df.format(currentItem.volume * currentItem.price).toString()

            when {
                per > 0 -> {
                    percent.setTextColor(Color.parseColor("#00E503"))
                    profit.setTextColor(Color.parseColor("#00E503"))
                }
                per < 0 -> {
                    percent.setTextColor(Color.parseColor("#FB0000"))
                    profit.setTextColor(Color.parseColor("#FB0000"))
                }
            }

            percent.text = "${df.format(per)}%"
            profit.text =
                "$" + "${df.format(currentItem.price / (100 + per) * per * currentItem.volume)}"
        }

        holder.binding.price.tag = currentItem
        holder.binding.root.tag = currentItem

    }
}