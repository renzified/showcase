package com.sleepyhead.patterns.mvvm.views.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sleepyhead.patterns.mvvm.R
import com.sleepyhead.patterns.mvvm.databinding.FragmentDetailBinding
import com.sleepyhead.patterns.mvvm.presentation.common.formatChangePercent
import com.sleepyhead.patterns.mvvm.presentation.common.formatPrice
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailEffect
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailIntent
import com.sleepyhead.patterns.mvvm.presentation.detail.DetailViewModel
import com.sleepyhead.patterns.mvvm.presentation.detailViewModelFactory
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val instrumentId: String by lazy {
        requireArguments().getString("instrumentId").orEmpty()
    }

    private val viewModel: DetailViewModel by viewModels {
        requireContext().detailViewModelFactory(instrumentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onIntent(DetailIntent.NavigateBack)
        }
        binding.favoriteAction.setOnClickListener {
            viewModel.onIntent(DetailIntent.ToggleFavorite)
        }
        binding.favoriteButton.setOnClickListener {
            viewModel.onIntent(DetailIntent.ToggleFavorite)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.loading.isVisible = state.isLoading
                        binding.errorText.isVisible = state.errorMessage != null
                        binding.errorText.text = state.errorMessage
                        val instrument = state.instrument
                        binding.content.isVisible = instrument != null && !state.isLoading
                        if (instrument != null) {
                            binding.toolbar.title = instrument.symbol
                            binding.name.text = instrument.name
                            binding.price.text = formatPrice(instrument.price, instrument.currency)
                            binding.change.text = formatChangePercent(instrument.changePercent)
                            binding.change.setTextColor(
                                if (instrument.changePercent >= 0) Color.parseColor("#1B7F4E")
                                else Color.parseColor("#B3261E"),
                            )
                            binding.sector.text = getString(R.string.sector_format, instrument.sector)
                            binding.description.text = instrument.description
                            binding.favoriteButton.setText(
                                if (instrument.isFavorite) {
                                    R.string.remove_from_favorites
                                } else {
                                    R.string.add_to_favorites
                                },
                            )
                            binding.favoriteAction.setImageResource(
                                if (instrument.isFavorite) {
                                    R.drawable.ic_favorite
                                } else {
                                    R.drawable.ic_favorite_border
                                },
                            )
                        }
                    }
                }
                launch {
                    viewModel.effects.collect { effect ->
                        when (effect) {
                            DetailEffect.NavigateBack -> findNavController().navigateUp()
                            is DetailEffect.ShowSnackbar -> {
                                Snackbar.make(binding.detailRoot, effect.message, Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
