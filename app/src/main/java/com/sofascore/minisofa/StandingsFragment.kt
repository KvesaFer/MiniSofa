package com.sofascore.minisofa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofascore.minisofa.databinding.FragmentStandingsBinding
import com.sofascore.minisofa.ui.StandingsAdapter
import com.sofascore.minisofa.ui.StandingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var binding: FragmentStandingsBinding
    private lateinit var standingsAdapter: StandingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        loadStandings() // Load standings on view created
    }

    private fun setupRecyclerView() {
        standingsAdapter = StandingsAdapter()
        binding.recyclerViewStandings.apply {
            adapter = standingsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.standings.observe(viewLifecycleOwner) { standings ->
            standingsAdapter.submitList(standings)
        }
    }

    private fun loadStandings() {
        // Assuming you pass the tournamentId to this fragment via arguments
        val tournamentId = arguments?.getInt("TOURNAMENT_ID") ?: return
        viewModel.loadStandings(tournamentId)
    }
}