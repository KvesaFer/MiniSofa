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
import com.sofascore.minisofa.utils.SportType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var binding: FragmentStandingsBinding
    private lateinit var standingsAdapter: StandingsAdapter
    private var tournamentId: Int = 0
    private lateinit var sportType: SportType

    companion object {
        private const val TOURNAMENT_ID_KEY = "TOURNAMENT_ID"
        private const val SPORT_TYPE_KEY = "SPORT_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("StandingsFragment", "onCreate called")
        initializeArguments()
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
        if (tournamentId == 0) return

        setupRecyclerView()
        observeViewModel()
        loadStandings()
    }

    private fun initializeArguments() {
        arguments?.let {
            tournamentId = it.getInt(TOURNAMENT_ID_KEY, 0)
            val sportTypeString = it.getString(SPORT_TYPE_KEY, "football")
            Log.d("StandingsFragment", "Sport Type String: $sportTypeString")
            sportType = SportType.fromApiName(sportTypeString) ?: SportType.FOOTBALL
        }
        Log.d("StandingsFragment", "Tournament ID: $tournamentId, SportType: $sportType")
    }

    private fun setupRecyclerView() {
        standingsAdapter = StandingsAdapter(sportType)
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
        Log.d("StandingsFragment", "Loading standings for tournament ID: $tournamentId, SportType: $sportType")
        viewModel.loadStandings(tournamentId, sportType)
    }
}
