package com.eaapps.onboarding.presentation.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eaapps.core.country.domain.entity.CountryEntity
import com.eaapps.onboarding.R
import com.eaapps.onboarding.databinding.FragmentOnBoardingBinding
import com.eaapps.onboarding.presentation.onBoarding.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private lateinit var binding: FragmentOnBoardingBinding
    private val viewModel: BoardingViewModel by viewModels()
    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter {
            viewModel.saveSelectCategory(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentOnBoardingBinding.inflate(inflater).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindClicks()
        binding.setupCategoriesRecyclerView()
        binding.collectBoardingState()

    }

    private fun FragmentOnBoardingBinding.setupCountrySpinner(countries: List<CountryEntity>) {
        val countryNames = countries.map { it.name }
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = spinnerAdapter
        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.saveChooseCountry(countries[position].code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun FragmentOnBoardingBinding.setupCategoriesRecyclerView() {
        recyclerCategories.adapter = adapter
    }

    private fun FragmentOnBoardingBinding.bindClicks() {
        buttonFinish.setOnClickListener {
            viewModel.finishSetup()
            findNavController().navigate(R.id.action_onBoardingFragment_to_headlineFragment)
        }
    }

    private fun FragmentOnBoardingBinding.collectBoardingState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    state.categories?.apply {
                        adapter.submitList(this)
                    }

                    state.countries?.apply {
                        setupCountrySpinner(this)
                    }

                    buttonFinish.isEnabled = state.enableFinish

                    state.error?.apply {
                        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}