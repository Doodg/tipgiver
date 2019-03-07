package com.enigma.givetip.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.enigma.givetip.R
import com.enigma.givetip.databinding.SaveTipCalculationsListItemBinding
import com.enigma.givetip.viewmodel.TipCalculationSummaryItem

class TipSummaryAdapter(val onItemSelectd: (item: TipCalculationSummaryItem) -> Unit) :
    RecyclerView.Adapter<TipSummaryAdapter.SummaryViewHolder>() {
    private val tipCalculationSummaries = mutableListOf<TipCalculationSummaryItem>()
    fun updateList(updatesItems: List<TipCalculationSummaryItem>) {
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updatesItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SaveTipCalculationsListItemBinding>(
            inflater, R.layout.save_tip_calculations_list_item, parent, false
        )

        return SummaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(tipCalculationSummaries[position])
    }

    inner class SummaryViewHolder(val binding: SaveTipCalculationsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TipCalculationSummaryItem) {
            binding.item = item
            binding.root.setOnClickListener {
                onItemSelectd(item)
            }
            binding.executePendingBindings()
        }

    }

}