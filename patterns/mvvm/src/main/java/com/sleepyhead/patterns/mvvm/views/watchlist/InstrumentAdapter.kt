package com.sleepyhead.patterns.mvvm.views.watchlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sleepyhead.patterns.mvvm.R
import com.sleepyhead.patterns.mvvm.data.model.Instrument
import com.sleepyhead.patterns.mvvm.databinding.ItemInstrumentBinding
import com.sleepyhead.patterns.mvvm.presentation.common.formatChangePercent
import com.sleepyhead.patterns.mvvm.presentation.common.formatPrice

class InstrumentAdapter(
    private val onItemClick: (Instrument) -> Unit,
    private val onFavoriteClick: (Instrument) -> Unit,
) : ListAdapter<Instrument, InstrumentAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInstrumentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemInstrumentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(instrument: Instrument) {
            binding.symbol.text = instrument.symbol
            binding.name.text = instrument.name
            binding.price.text = formatPrice(instrument.price, instrument.currency)
            binding.change.text = formatChangePercent(instrument.changePercent)
            binding.change.setTextColor(
                if (instrument.changePercent >= 0) Color.parseColor("#1B7F4E")
                else Color.parseColor("#B3261E"),
            )
            binding.favoriteButton.setImageResource(
                if (instrument.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
            )
            binding.favoriteButton.contentDescription = binding.root.context.getString(
                if (instrument.isFavorite) R.string.remove_from_favorites else R.string.add_to_favorites,
            )
            binding.root.setOnClickListener { onItemClick(instrument) }
            binding.favoriteButton.setOnClickListener { onFavoriteClick(instrument) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Instrument>() {
        override fun areItemsTheSame(oldItem: Instrument, newItem: Instrument): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Instrument, newItem: Instrument): Boolean =
            oldItem == newItem
    }
}
