package com.scfnotification.notifyme.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scfnotification.notifyme.data.adapters.CoinWithValuesAdapter
import com.scfnotification.notifyme.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val adapter = CoinWithValuesAdapter()
        binding.favRecycler.adapter = adapter
        binding.favRecycler.layoutManager = LinearLayoutManager(context)

        binding.addFav.setOnClickListener {
            view?.let { it1 -> fragmentSwitch(it1) }
        }
        favouritesViewModel.update(requireContext())

        showFavorites(adapter, binding)
//        val itemsSwipeRefresh = root.findViewById<SwipeRefreshLayout>(R.id.fav_swipe)
//        itemsSwipeRefresh.setProgressBackgroundColorSchemeColor(
//            ContextCompat.getColor(
//                currentContext,
//                R.color.light_orange
//            )
//        )
//        itemsSwipeRefresh.setColorSchemeColors(Color.WHITE)
//        itemsSwipeRefresh.setOnRefreshListener {
//            favouritesViewModel.update(currentContext)
//        }
        return binding.root
    }

    private fun showFavorites(
        adapter: CoinWithValuesAdapter,
        binding: FragmentFavouritesBinding
    ) {
        favouritesViewModel.getFavorites.observe(
            viewLifecycleOwner,
            {
                binding.hasCoins = !it.isNullOrEmpty()
                adapter.submitList(it)
            }
        )
    }

    private fun fragmentSwitch(view: View) {
        val direction =
            FavouritesFragmentDirections.actionNavigationFavouritesToNavigationHome()
        view.findNavController().navigate(direction)
    }
}
