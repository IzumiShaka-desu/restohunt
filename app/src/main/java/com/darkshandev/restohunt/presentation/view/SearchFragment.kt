package com.darkshandev.restohunt.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.darkshandev.restohunt.R
import com.darkshandev.restohunt.core.domain.models.AppState
import com.darkshandev.restohunt.databinding.FragmentSearchBinding
import com.darkshandev.restohunt.presentation.view.adapter.ListViewAdapter
import com.darkshandev.restohunt.presentation.viewmodel.RestaurantViewmodel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val adapterM = ListViewAdapter {
        val bundle = bundleOf("idRestaurant" to it)
        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, args = bundle)
    }
    private val _viewmodel by activityViewModels<RestaurantViewmodel>()
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupUi()
        setupOberver()
        return _binding?.root
    }

    private fun setupOberver() {

        lifecycleScope.launch {
            _viewmodel.restaurantSearchResult
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { result ->
                    when (result) {
                        is AppState.Error -> {
                            _binding?.apply {
                                errorSearchTv.text = result.message
                                loadingSearch.visibility = View.GONE
                                loadingSearch.pauseAnimation()
                                rvSearchUser.visibility = View.GONE
                                notFoundAnim.visibility = View.VISIBLE
                                notFoundAnim.playAnimation()
                                errorSearch.visibility = View.VISIBLE
                            }
                        }
                        is AppState.Initial -> {
                            _binding?.apply {
                                errorSearchTv.text =
                                    "type food or restaurant name to explore best restaurant"
                                loadingSearch.visibility = View.GONE
                                loadingSearch.pauseAnimation()
                                rvSearchUser.visibility = View.GONE
                                notFoundAnim.visibility = View.GONE
                                notFoundAnim.pauseAnimation()
                                errorSearch.visibility = View.VISIBLE
                            }
                        }
                        is AppState.Loading -> {
                            _binding?.apply {
                                notFoundAnim.pauseAnimation()
                                loadingSearch.visibility = View.VISIBLE
                                loadingSearch.playAnimation()
                                rvSearchUser.visibility = View.GONE
                                errorSearch.visibility = View.GONE
                            }
                        }
                        is AppState.Success -> {
                            _binding?.apply {
                                loadingSearch.visibility = View.GONE
                                val newList = result.data ?: emptyList()
                                if (newList.isEmpty()) {
                                    rvSearchUser.visibility = View.GONE
                                    errorSearchTv.text = "Cannot find any user you want"
                                    errorSearchTv.text = result.message
                                    loadingSearch.visibility = View.GONE
                                    loadingSearch.pauseAnimation()
                                    notFoundAnim.visibility = View.VISIBLE
                                    notFoundAnim.playAnimation()
                                    errorSearch.visibility = View.VISIBLE
                                } else {
                                    adapterM.submitList(newList)
                                    rvSearchUser.visibility = View.VISIBLE
                                    errorSearchTv.visibility = View.GONE
                                }

                            }
                        }
                    }
                }
        }
    }

    private fun setupUi() {
        _binding?.apply {
            rvSearchUser.adapter = adapterM
            searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                override fun onQueryTextChange(newText: String): Boolean {
                    _viewmodel.updateSearchQuery(newText)
                    return true
                }
            })

        }
    }


}