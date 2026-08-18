package com.sleepyhead.patterns.mvvm.views.watchlist

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sleepyhead.patterns.mvvm.R
import com.sleepyhead.patterns.mvvm.databinding.FragmentWatchlistBinding
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistEffect
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistIntent
import com.sleepyhead.patterns.mvvm.presentation.watchlist.WatchlistViewModel
import com.sleepyhead.patterns.mvvm.presentation.watchlistViewModelFactory
import kotlinx.coroutines.launch

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchlistViewModel by viewModels {
        requireContext().watchlistViewModelFactory()
    }

    private val adapter = InstrumentAdapter(
        onItemClick = { instrument ->
            viewModel.onIntent(WatchlistIntent.OpenDetails(instrument.id))
        },
        onFavoriteClick = { instrument ->
            viewModel.onIntent(WatchlistIntent.ToggleFavorite(instrument.id))
        },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { requireActivity().finish() }
        binding.instrumentList.layoutManager = LinearLayoutManager(requireContext())
        binding.instrumentList.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onIntent(WatchlistIntent.Refresh)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        binding.loading.isVisible = state.isLoading
                        binding.swipeRefresh.isRefreshing = state.isRefreshing
                        binding.swipeRefresh.isVisible = !state.isLoading
                        binding.errorText.isVisible =
                            state.errorMessage != null && state.instruments.isEmpty()
                        binding.errorText.text = state.errorMessage
                        adapter.submitList(state.instruments)
                    }
                }
                launch {
                    viewModel.effects.collect { effect ->
                        when (effect) {
                            is WatchlistEffect.NavigateToDetails -> {
                                val args = Bundle().apply {
                                    putString("instrumentId", effect.instrumentId)
                                }
                                findNavController().navigate(
                                    R.id.action_watchlist_to_detail,
                                    args,
                                )
                            }
                            is WatchlistEffect.ShowSnackbar -> {
                                Snackbar.make(binding.watchlistRoot, effect.message, Snackbar.LENGTH_SHORT)
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
