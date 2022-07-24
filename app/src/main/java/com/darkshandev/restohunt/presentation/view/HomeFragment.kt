package com.darkshandev.restohunt.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.darkshandev.restohunt.R
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.databinding.FragmentHomeBinding
import com.darkshandev.restohunt.presentation.view.adapter.ListViewAdapter
import com.darkshandev.restohunt.presentation.viewmodel.RestaurantViewmodel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val _viewModel by activityViewModels<RestaurantViewmodel>()
    private val adapterM = ListViewAdapter {
        val bundle = bundleOf("idRestaurant" to it)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, args = bundle)
    }

    override fun onDestroyView() {
        _binding?.rvResto?.adapter=null
        _binding = null
        super.onDestroyView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        _binding?.apply {
            rvResto.apply {
                adapter = adapterM
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            }
        }
        setupOberver()
        return _binding?.root
    }

    private fun setupOberver() {
        lifecycleScope.launchWhenResumed {
            _viewModel.restaurant.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is AppState.Initial -> {
//                        _binding?.apply {
//
//                        }
                    }
                    is AppState.Error -> {
                        _binding?.apply {
                            rvResto.visibility = View.GONE
                            errorMessage.text = it.message
                            errorMessage.visibility = View.VISIBLE
                            animationView.visibility = View.GONE
                            animationView.pauseAnimation()
                        }

                    }
                    is AppState.Loading -> {
                        _binding?.apply {
                            rvResto.visibility = View.GONE
                            errorMessage.text = it.message
                            errorMessage.visibility = View.GONE
                            animationView.visibility = View.VISIBLE
                            animationView.playAnimation()
                        }

                    }
                    is AppState.Success -> {
                        adapterM.submitList(it.data)
                        _binding?.apply {
                            rvResto.visibility = View.VISIBLE
                            errorMessage.visibility = View.GONE
                            animationView.visibility = View.GONE
                            animationView.pauseAnimation()
                        }

                    }
                }
            }

        }
    }


}