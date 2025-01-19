@file:OptIn(FlowPreview::class)

package com.eaapps.search.presentation.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eaapps.core.base.error.getErrorMessage
import com.eaapps.core.base.extensions.textInputAsFlow
import com.eaapps.search.R
import com.eaapps.search.databinding.FragmentSearchBinding
import com.eaapps.search.presentation.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchAdapter by lazy {
        SearchAdapter(onSelectFavorite = {
            viewModel.addArticleToFavorite(it)
        }, onSelectHeadline = {
            try {
                requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            } catch (_: ActivityNotFoundException) {
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentSearchBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()

        }
        binding.bindTextField()
        binding.bindSearchRecyclerView()
        binding.collectSearchState()

    }

    private fun FragmentSearchBinding.bindTextField() {
        searchTextFiled.textInputAsFlow().map {
            it.toString()
        }.debounce(500)
            .onEach {
                if (it.isNotEmpty())
                    viewModel.searchForArticle(it, category = viewModel.uiState.value.selectionCategory ?: "")
            }.launchIn(lifecycleScope)

    }

    private fun FragmentSearchBinding.setupCategorySpinner(categories: List<String>, selection: Int = 0) {
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = spinnerAdapter
        spinnerCountry.setSelection(selection)
        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.updateSelectionCategory(position, categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    private fun FragmentSearchBinding.bindSearchRecyclerView() {
        searchRecycler.adapter = adapter
    }

    private fun FragmentSearchBinding.collectSearchState() {
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    loadingIndicator.visibility = if (it.loading) View.VISIBLE else View.GONE

                    if (it.loading)
                        errorText.visibility = View.GONE

                    it.categories?.apply {
                        setupCategorySpinner(this, it.selectionCategoryPosition ?: 0)
                    }

                    it.data?.apply {
                        searchRecycler.visibility = View.VISIBLE
                        errorText.visibility = View.GONE
                        adapter.submitList(this)
                    } ?: run {
                        adapter.submitList(emptyList())
                        searchRecycler.visibility = View.GONE
                    }

                    it.error?.getErrorMessage()?.let { errorMessage ->
                        errorText.text = errorMessage
                        errorText.visibility = View.VISIBLE
                    } ?: run {
                        errorText.visibility = View.GONE
                    }

                }
            }
        }
    }

}