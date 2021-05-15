package com.example.scfnotification.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.scfnotification.R
import com.example.scfnotification.data.entities.CoinWithValues
import com.example.scfnotification.ui.details.DetailFragment.Callback
import com.example.scfnotification.ui.main.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).hideBottomNav()
        detailViewModel =
            ViewModelProvider(this).get(DetailViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false).apply {
            viewLifecycleOwner
            Callback { coin ->
                coin?.let {
                    hideAppBarFab(rootView.findViewById(R.id.fab))
                    detailViewModel.favorite(context, coin.coin)
                }
            }
        }
//        val coinDetailScrollview: ScrollView = root.findViewById(R.id.coin_detail_scrollview)
        val toolbar: MaterialToolbar = root.findViewById(R.id.toolbar)
        var isToolbarShown = false

        // scroll change listener begins at Y = 0 when image is fully collapsed
//        coinDetailScrollview.setOnScrollChangeListener(
//            NestedScrollView.OnScrollChangeListener() { _, _, scrollY, _, _ ->
//
//                // User scrolled past image to height of toolbar and the title text is
//                // underneath the toolbar, so the toolbar should be shown.
//                val shouldShowToolbar = scrollY > toolbar.height
//
//                // The new state of the toolbar differs from the previous state; update
//                // appbar and toolbar attributes.
//                if (isToolbarShown != shouldShowToolbar) {
//                    isToolbarShown = shouldShowToolbar
//
//                    // Use shadow animator to add elevation if toolbar is shown
//                    appbar.isActivated = shouldShowToolbar
//
//                    // Show the plant name if toolbar is shown
//                    toolbarLayout.isTitleEnabled = shouldShowToolbar
//                }
//            }
//        )

        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
        return root
    }

    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNav()
    }

    fun interface Callback {
        fun add(coin: CoinWithValues?)
    }
}
