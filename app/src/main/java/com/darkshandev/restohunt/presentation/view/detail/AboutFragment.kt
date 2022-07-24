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
import com.darkshandev.restohunt.databinding.FragmentAboutBinding
import com.darkshandev.restohunt.presentation.viewmodel.RestaurantViewmodel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch


class AboutFragment : Fragment() {
    private val _viewModel by activityViewModels<RestaurantViewmodel>()
    private var _binding: FragmentAboutBinding? = null
    override fun onDestroyView() {
        _binding?.scrollView?.removeAllViews()
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(layoutInflater)
        lifecycleScope.launch {
            _viewModel
                .detailRestaurant
                .flowWithLifecycle(lifecycle)
                .collect {
                    when (it) {
                        is AppState.Error -> {
                        }
                        is AppState.Initial -> {

                        }
                        is AppState.Loading -> {
                        }
                        is AppState.Success -> {
                            _binding?.detail = it.data
                            _binding?.foodChips?.apply {
                                removeAllViews()
                                it.data?.menus?.foods?.forEach { food ->
                                    val chip = Chip(requireContext())
                                    chip.text = food.name
                                    chip.chipIcon = AppCompatResources.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_baseline_restaurant_menu_24
                                    )
                                    addView(chip)
                                }
                            }
                            _binding?.drinkChips?.apply {
                                removeAllViews()
                                it.data?.menus?.drinks?.forEach { drink ->
                                    val chip = Chip(requireContext())
                                    chip.text = drink.name
                                    chip.chipIcon =
                                        AppCompatResources.getDrawable(
                                            requireContext(),
                                            R.drawable.ic_baseline_restaurant_menu_24
                                        )
                                    addView(chip)
                                }
                            }
                        }
                    }
                }
        }
        return _binding?.root
    }


}