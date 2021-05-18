package com.scfnotification.notifyme.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scfnotification.notifyme.data.entities.CoinWithValues
import com.scfnotification.notifyme.databinding.RowLayoutBinding
import com.scfnotification.notifyme.ui.favourites.FavouritesFragmentDirections
import com.scfnotification.notifyme.ui.home.HomeFragment
import com.scfnotification.notifyme.ui.home.HomeFragmentDirections

class CoinWithValuesAdapter :
    ListAdapter<CoinWithValues, RecyclerView.ViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CoinsViewHolder(
            RowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val coin = getItem(position)
        (holder as CoinsViewHolder).bind(coin)
    }

    class CoinsViewHolder(
        private val binding: RowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            SavedStateHandle().apply {
                set("coinId", binding.coin?.coin?.id)
            }
            binding.setClickListener {
                binding.coin?.let { coin ->
                    navigateToCoin(coin, it)
                }
            }
        }

        private fun navigateToCoin(
            coin: CoinWithValues,
            view: View
        ) {
            val activeFragment: Fragment = FragmentManager.findFragment(view)
            val direction = if (activeFragment is HomeFragment) {
                HomeFragmentDirections.actionNavigationHomeToNavigationDetail(coin.coin.id)
            } else {
                FavouritesFragmentDirections.actionNavigationFavouritesToNavigationDetail(coin.coin.id)
            }
            view.findNavController().navigate(direction)
        }

        fun bind(item: CoinWithValues) {
            binding.apply {
                coin = item
                executePendingBindings()
            }
        }
    }
}

private class CoinDiffCallback : DiffUtil.ItemCallback<CoinWithValues>() {

    override fun areItemsTheSame(oldItem: CoinWithValues, newItem: CoinWithValues): Boolean {
        return oldItem.coin.id == newItem.coin.id
    }

    override fun areContentsTheSame(oldItem: CoinWithValues, newItem: CoinWithValues): Boolean {
        return oldItem == newItem
    }
}
