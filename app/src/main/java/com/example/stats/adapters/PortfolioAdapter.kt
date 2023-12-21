package com.example.stats

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stats.databinding.PortfolioItemBinding
import com.example.stats.model.crypto_cost
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

        val currentItem = potfolioList[position]
        val price_:Double = crypto_cost.getOrDefault(currentItem.ticker,0.0)

        val per = ((price_ * currentItem.quantity - currentItem.total_invested)/currentItem.total_invested)*100

        with(holder.binding) {
            Ticker.text = currentItem.ticker
            volume.text = df.format(currentItem.quantity).toString() + " " + currentItem.ticker
            price.text = "$" + df.format(crypto_cost.getOrDefault(currentItem.ticker,0)).toString()
            cost.text = "$" + df.format(currentItem.quantity *  price_).toString()

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
                "$" + "${df.format(price_ / (100 + per) * per * currentItem.quantity)}"
        }

        holder.binding.price.tag = currentItem
        holder.binding.root.tag = currentItem

    }
}