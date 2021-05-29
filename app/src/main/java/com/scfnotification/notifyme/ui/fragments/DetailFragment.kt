package com.scfnotification.notifyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.scfnotification.notifyme.R
import com.scfnotification.notifyme.data.entities.CoinWithValues
import com.scfnotification.notifyme.databinding.FragmentDetailBinding
import com.scfnotification.notifyme.ui.activities.MainActivity
import com.scfnotification.notifyme.ui.fragments.DetailFragment.Callback
import com.scfnotification.notifyme.ui.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).hideBottomNav()
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).apply {
            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback {
                hideAppBarFab(fabAddFav)
                showAppBarFab(removeFav)
                Log.d("Callback", "add $it to fav")
                detailViewModel.favorite(it.coin.id, requireContext())
                Snackbar.make(
                    root,
                    "Added to favorites",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            setRemoveFav { coin ->
                hideAppBarFab(removeFav)
                showAppBarFab(fabAddFav)
                detailViewModel.removeFavorite(coin.coin.id, requireContext())
                Snackbar.make(
                    root,
                    "Removed from favorites",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            var isToolbarShown = false
            detailViewModel.showFav()

//         scroll change listener begins at Y = 0 when image is fully collapsed

            coinDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }
    private fun showAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.show()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNav()
    }

    fun interface Callback {
        fun setFav(coin: CoinWithValues)
    }

    fun interface RemoveFav {
        fun removeFav(coin: CoinWithValues)
    }
}
