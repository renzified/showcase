package com.sleepyhead.patterns.mvi.views.watchlist

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
import com.sleepyhead.patterns.mvi.R
import com.sleepyhead.patterns.mvi.databinding.FragmentWatchlistBinding
import com.sleepyhead.patterns.mvi.presentation.watchlist.WatchlistEffect
import com.sleepyhead.patterns.mvi.presentation.watchlist.WatchlistIntent
import com.sleepyhead.patterns.mvi.presentation.watchlist.WatchlistStore
import com.sleepyhead.patterns.mvi.presentation.watchlistStoreFactory
import kotlinx.coroutines.launch

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val store: WatchlistStore by viewModels {
        requireContext().watchlistStoreFactory()
    }

    private val adapter = InstrumentAdapter(
        onItemClick = { instrument ->
            store.dispatch(WatchlistIntent.OpenDetails(instrument.id))
        },
        onFavoriteClick = { instrument ->
            store.dispatch(WatchlistIntent.ToggleFavorite(instrument.id))
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
        binding.toolbar.title = getString(R.string.watchlist_views_title)
        binding.toolbar.setNavigationOnClickListener { requireActivity().finish() }
        binding.instrumentList.layoutManager = LinearLayoutManager(requireContext())
        binding.instrumentList.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            store.dispatch(WatchlistIntent.Refresh)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    store.state.collect { state ->
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
                    store.effects.collect { effect ->
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
