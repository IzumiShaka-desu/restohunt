package com.darkshandev.restohunt.presentation.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.databinding.FragmentReviewBinding
import com.darkshandev.restohunt.presentation.view.adapter.ReviewListViewAdapter
import com.darkshandev.restohunt.presentation.viewmodel.RestaurantViewmodel
import kotlinx.coroutines.launch

class ReviewFragment : Fragment() {

    private val _viewModel by activityViewModels<RestaurantViewmodel>()
    private var _binding: FragmentReviewBinding? = null
    private val reviewAdapter = ReviewListViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = FragmentReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding?.apply {
            rvReview.adapter = reviewAdapter
            rvReview.layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            _viewModel.detailRestaurant.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is AppState.Error -> {
                    }
                    is AppState.Initial -> {

                    }
                    is AppState.Loading -> {
                    }
                    is AppState.Success -> {
                        reviewAdapter.submitList(it.data?.customerReviews)
                    }
                }
            }
        }
        return _binding?.root
    }

}