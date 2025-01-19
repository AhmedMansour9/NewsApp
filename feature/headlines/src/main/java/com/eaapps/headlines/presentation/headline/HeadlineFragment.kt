package com.eaapps.headlines.presentation.headline

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eaapps.headlines.R
import com.eaapps.headlines.databinding.FragmentHeadlineBinding
import com.eaapps.headlines.presentation.headline.adapter.TopHeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeadlineFragment : Fragment(R.layout.fragment_headline) {

    private lateinit var binding: FragmentHeadlineBinding
    private val viewModel: HeadlineViewModel by viewModels()
    private val adapter: TopHeadlineAdapter by lazy {
        TopHeadlineAdapter(onSelectFavorite = {
            viewModel.addArticleToFavorite(it)
        }, onSelectHeadline = {
            try{
                findNavController().navigate(R.id.action_headlineFragment_to_favoriteFragment)
//                requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }catch (_:ActivityNotFoundException){}
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentHeadlineBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindTopHeadlineRecyclerView()
        binding.collectTopHeadline()

    }

    private fun FragmentHeadlineBinding.bindTopHeadlineRecyclerView() {
        topHeadlineRecycler.adapter = adapter
    }

    private fun FragmentHeadlineBinding.collectTopHeadline() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    loadingIndicator.visibility = if (it.loading) View.VISIBLE else View.GONE
                    it.data?.apply {
                        topHeadlineRecycler.visibility = View.VISIBLE
                        adapter.submitList(this)
                    } ?: run {
                        adapter.submitList(emptyList())
                        topHeadlineRecycler.visibility = View.GONE
                    }

                 }
            }
        }
    }

}