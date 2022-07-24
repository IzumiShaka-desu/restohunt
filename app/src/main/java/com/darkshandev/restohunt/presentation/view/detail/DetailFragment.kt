package com.darkshandev.restohunt.presentation.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.darkshandev.restohunt.R
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.databinding.FragmentDetailBinding
import com.darkshandev.restohunt.presentation.view.adapter.TabViewPagerAdapter
import com.darkshandev.restohunt.presentation.viewmodel.RestaurantViewmodel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private val _viewModel by activityViewModels<RestaurantViewmodel>()
    private var _binding: FragmentDetailBinding? = null
    private val tabsTitle = listOf("about", "review")

    private var viewPagerAdapter: TabViewPagerAdapter? = null

    override fun onDestroyView() {
        viewPagerAdapter=null
        _binding = null
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idRestaurant")?.let {
            _viewModel.setSelectedId(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        viewPagerAdapter =
            TabViewPagerAdapter(
                listOf(AboutFragment(), ReviewFragment()),
                fragmentManager = parentFragmentManager,
                lifecycle
            )
        _binding?.apply {
            vp.adapter = viewPagerAdapter
            vp.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, vp) { tab, position ->
                tab.text = tabsTitle[position]
            }.attach()
            fabFav.setOnClickListener {
                _viewModel.toggleFavorite()
            }
        }
        lifecycleScope.launch {
            _viewModel.isFavorite.flowWithLifecycle(lifecycle).collect {
                it?.let {
                    _binding?.apply {
                        if (it) fabFav.setImageDrawable(
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_baseline_favorite_24
                            )
                        )
                        else fabFav.setImageDrawable(
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_baseline_favorite_border_24
                            )
                        )
                    }

                }
            }
        }
        lifecycleScope.launch {
            _viewModel.detailRestaurant.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is AppState.Error -> {
                        _binding?.apply {
                            errorMessage.text = it.message
                            errorMessage.visibility = View.VISIBLE
                            animationView.visibility = View.GONE
                            animationView.pauseAnimation()
                            contentPanel.visibility = View.GONE
                        }
                    }
                    is AppState.Initial -> {

                    }
                    is AppState.Loading -> {
                        _binding?.apply {
                            errorMessage.visibility = View.GONE
                            animationView.visibility = View.VISIBLE
                            animationView.playAnimation()
                            contentPanel.visibility = View.GONE
                        }
                    }
                    is AppState.Success -> {
                        _binding?.detail = it.data
                        _binding?.apply {
                            errorMessage.visibility = View.GONE
                            animationView.visibility = View.GONE
                            animationView.pauseAnimation()
                            contentPanel.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        return _binding?.root
    }

}