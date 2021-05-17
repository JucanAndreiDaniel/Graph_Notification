package com.example.scfnotification.ui.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scfnotification.data.adapters.CoinWithValuesAdapterList
import com.example.scfnotification.databinding.FragmentFavouritesBinding
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
        val adapter = CoinWithValuesAdapterList()
        binding.favRecycler.adapter = CoinWithValuesAdapterList()
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
        adapter: CoinWithValuesAdapterList,
        binding: FragmentFavouritesBinding
    ) {
        favouritesViewModel.getFavorites.observe(
            viewLifecycleOwner,
            {
                binding.hasCoins = !it.isNullOrEmpty()
                adapter.submitList(it)
            }
        )
//        favouritesViewModel.getFavorites.observe(viewLifecycleOwner) { result ->
//            binding.hasCoins = !result.isNullOrEmpty()
//            Log.d("hasCoins", binding.hasCoins.toString())
//            adapter.submitList(result)
//        }
    }

    private fun fragmentSwitch(view: View) {
        val direction =
            FavouritesFragmentDirections.actionNavigationFavouritesToNavigationHome()
        view.findNavController().navigate(direction)
    }
}
