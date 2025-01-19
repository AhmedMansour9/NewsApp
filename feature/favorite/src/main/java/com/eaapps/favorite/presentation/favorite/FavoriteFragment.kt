package com.eaapps.favorite.presentation.favorite

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eaapps.favorite.R
import com.eaapps.favorite.databinding.FragmentFavoriteBinding
import com.eaapps.favorite.presentation.favorite.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(onRemoveFavorite = {
            viewModel.removeArticleFromFavorite(it.url)
        }, onSelectHeadline = {
            try {
                requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            } catch (_: ActivityNotFoundException) {
            }
        })
    }
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentFavoriteBinding.inflate(inflater).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.setupFavoriteRecyclerView()
        binding.collectFavoriteState()
    }

    private fun FragmentFavoriteBinding.setupFavoriteRecyclerView() {
        favoriteRecycler.adapter = adapter
    }

    private fun FragmentFavoriteBinding.collectFavoriteState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    loadingIndicator.visibility = if (it.loading) View.VISIBLE else View.GONE
                    it.data?.apply {
                        if (isNotEmpty()) {
                            favoriteRecycler.visibility = View.VISIBLE
                            noFavoriteMsg.visibility = View.GONE
                            adapter.submitList(this)
                        } else {
                            favoriteRecycler.visibility = View.GONE
                            noFavoriteMsg.visibility = View.VISIBLE
                        }
                    } ?: run {
                        noFavoriteMsg.visibility = View.VISIBLE
                        adapter.submitList(emptyList())
                        favoriteRecycler.visibility = View.GONE
                    }
                }
            }
        }
    }

}