package com.darkshandev.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.artworkspace.favorite.DaggerFavoritesComponent
import com.darkshandev.favorites.databinding.FragmentFavoriteBinding
import com.darkshandev.restohunt.app.DI.FavoritesModuleDependecies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val adapterM = FavListViewAdapter {
        val bundle = bundleOf("idRestaurant" to it)
        findNavController().navigate(
            com.darkshandev.restohunt.R.id.action_favoriteFragment_to_detailFragment,
            args = bundle
        )
    }

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewmodel by viewModels { factory }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoritesComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoritesModuleDependecies::class.java
                )
            )
            .build()
            .inject(this)
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding?.apply {
            rvFav.apply {
                adapter = adapterM
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        setupOberver()
        return _binding?.root
    }

    private fun setupOberver() {
        lifecycleScope.launch {
            viewModel.favRestaurant.flowWithLifecycle(lifecycle).collect {
                if (it.isNotEmpty()) {
                    adapterM.submitList(it)
                    _binding?.errorMessage?.visibility = View.GONE
                } else {
                    adapterM.submitList(it)
                    _binding?.errorMessage?.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.fav_empty)
                    }

                }
            }

        }
    }


}