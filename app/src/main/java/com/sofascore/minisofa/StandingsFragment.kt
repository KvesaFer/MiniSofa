package com.sofascore.minisofa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofascore.minisofa.databinding.FragmentStandingsBinding
import com.sofascore.minisofa.ui.adapters.StandingsAdapter
import com.sofascore.minisofa.ui.viewmodels.StandingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var binding: FragmentStandingsBinding
    private lateinit var standingsAdapter: StandingsAdapter
    private var tournamentId: Int = 0

    companion object {
        private const val TOURNAMENT_ID_KEY = "TOURNAMENT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("StandingsFragment", "onCreateView called")
        binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("StandingsFragment", "onViewCreated called")
        initializeTournamentId()
        if (tournamentId == 0) return

        setupRecyclerView()
        observeViewModel()
        loadStandings()
    }

    private fun initializeTournamentId() {
        arguments?.let {
            tournamentId = it.getInt(TOURNAMENT_ID_KEY, 0)
        }
        Log.d("StandingsFragment", "Tournament ID: $tournamentId")
    }

    private fun setupRecyclerView() {
        Log.d("StandingsFragment", "Setting up RecyclerView")
        standingsAdapter = StandingsAdapter()
        binding.recyclerViewStandings.apply {
            adapter = standingsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.standings.observe(viewLifecycleOwner) { standings ->
            Log.d("StandingsFragment", "Received events: $standings")
            standingsAdapter.submitList(standings?.toMutableList())
        }
    }

    private fun loadStandings() {
        val tournamentId = arguments?.getInt(TOURNAMENT_ID_KEY) ?: return
        Log.d("StandingsFragment", "Loading standings for tournament ID: $tournamentId")
        viewModel.loadStandings(tournamentId)
    }
}
