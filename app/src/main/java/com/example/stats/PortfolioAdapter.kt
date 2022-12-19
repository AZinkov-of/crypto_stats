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
//    fun deleteAsset(asset: Asset) {
//    }
}

class PortfolioAdapter(private val actionListener: ActionListener) :
    RecyclerView.Adapter<PortfolioAdapter.MyViewHolder>(), View.OnClickListener {
    //    private var potfolioList = emptyList<Int>()
    var potfolioList:List<Asset> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

//    private var wordList = emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PortfolioItemBinding.inflate(inflater, parent, false)
//        binding.root.setOnClickListener(this)
//        binding.cross.setOnClickListener(this)
        return MyViewHolder(binding)
    }

    class MyViewHolder(
        val binding: PortfolioItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

//    override fun onClick(v: View) {
////        val word: Word = v.tag as Word
//        when (v.id) {
////            R.id.cross -> {
////                actionListener.deleteItem(word)
////            }
//
//            else -> {
////                actionListener.deleteItem(word)
//            }
//        }
//    }

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
        profit.text = "$" + "${df.format(currentItem.price / (100 + per) * per * currentItem.volume)}"
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun setData(asset: List<Asset>) {
        potfolioList = asset
        notifyDataSetChanged()
    }


//    fun setData(words: List<Word>) {
//        val difUpdate = DiffUtil.calculateDiff(WordDiffCallback(wordList, words))
//        wordList = words
//        difUpdate.dispatchUpdatesTo(this)
//    }
}