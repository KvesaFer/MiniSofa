package com.sofascore.minisofa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sofascore.minisofa.databinding.FragmentMatchesBinding
import com.sofascore.minisofa.ui.EventAdapter
import com.sofascore.minisofa.ui.MatchesAdapter
import com.sofascore.minisofa.ui.MatchesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private val viewModel: MatchesViewModel by viewModels()
    private lateinit var binding: FragmentMatchesBinding
    private lateinit var matchesAdapter: MatchesAdapter
    private var tournamentId: Int = 0

    companion object {
        private const val TOURNAMENT_ID_KEY = "TOURNAMENT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MatchesFragment", "onCreateView called")
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeTournamentId()
        if (tournamentId == 0) return

        setupRecyclerView()
        observeViewModel()
        loadInitialMatches()
    }

    private fun initializeTournamentId() {
        arguments?.let {
            tournamentId = it.getInt(TOURNAMENT_ID_KEY, 0)
        }
    }

    private fun setupRecyclerView() {
        matchesAdapter = MatchesAdapter()
        binding.recyclerViewMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    handleScroll(recyclerView)
                }
            })
        }
    }

    private fun handleScroll(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (firstVisibleItem <= 2) {
            viewModel.loadPreviousMatches(tournamentId)
        } else if (lastVisibleItem >= totalItemCount - 2) {
            viewModel.loadUpcomingMatches(tournamentId)
        }
    }

    private fun observeViewModel() {
        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            matchesAdapter.submitList(matches)
        }
    }

    private fun loadInitialMatches() {
        Log.d("MatchesFragment", "Loading initial matches for tournament ID: $tournamentId")
        viewModel.loadInitialMatches(tournamentId)
    }
}