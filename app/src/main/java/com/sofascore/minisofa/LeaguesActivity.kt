package com.sofascore.minisofa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.databinding.ActivityLeaguesBinding
import com.sofascore.minisofa.ui.adapters.LeagueAdapter
import com.sofascore.minisofa.ui.viewmodels.LeaguesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaguesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaguesBinding
    private lateinit var leagueAdapter: LeagueAdapter
    private val viewModel: LeaguesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LeaguesActivity", "onCreate called")
        binding = ActivityLeaguesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLeagues.root.findViewById(R.id.toolbar))

        val tw: TextView = binding.toolbarLeagues.root.findViewById(R.id.tw_leagues)
        tw.text = "Leagues"

        setupRecyclerView()
        setupTabs()
        setupClickListeners()

        viewModel.tournaments.observe(this) { tournaments ->
            Log.d("LeaguesActivity", "Observed tournaments: ${tournaments.size} items")
            leagueAdapter.submitList(tournaments)
        }

        loadLeagues("football") // Default load for football
    }

    private fun setupClickListeners() {
        val backButton: ImageView = binding.toolbarLeagues.root.findViewById(R.id.backButton)
        backButton.setOnClickListener{
            Log.d("LeaguesActivity", "Finished; Back Button clicked")
            finish()
        }
    }

    private fun setupRecyclerView() {
        Log.d("LeaguesActivity", "Setting up RecyclerView")
        leagueAdapter = LeagueAdapter { league ->
            onLeagueClicked(league)
        }
        binding.rwLeagues.apply {
            adapter = leagueAdapter
            layoutManager = LinearLayoutManager(this@LeaguesActivity)
        }
    }

    private fun setupTabs() {
        val inflater = LayoutInflater.from(this)

        val tabFootball = binding.tabLayoutLeagues.newTab()
        tabFootball.customView = getTabView(inflater, R.drawable.ic_football, "Football")
        binding.tabLayoutLeagues.addTab(tabFootball)

        val tabBasketball = binding.tabLayoutLeagues.newTab()
        tabBasketball.customView = getTabView(inflater, R.drawable.ic_basketball, "Basketball")
        binding.tabLayoutLeagues.addTab(tabBasketball)

        val tabAmericanFootball = binding.tabLayoutLeagues.newTab()
        tabAmericanFootball.customView = getTabView(inflater, R.drawable.ic_american_football, "Am. Football")
        binding.tabLayoutLeagues.addTab(tabAmericanFootball)

        binding.tabLayoutLeagues.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab selection
                tab?.let {
                    val sport = when (it.position) {
                        0 -> "football"
                        1 -> "basketball"
                        2 -> "american_football"
                        else -> "football"
                    }
                    loadLeagues(sport)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getTabView(inflater: LayoutInflater, iconResId: Int, text: String): View {
        val view = inflater.inflate(R.layout.custom_tab_layout, null)
        val tabIcon = view.findViewById<ImageView>(R.id.tab_icon)
        val tabText = view.findViewById<TextView>(R.id.tab_text)
        tabIcon.setImageResource(iconResId)
        tabText.text = text
        return view
    }

    private fun loadLeagues(sportSlug: String) {
        Log.d("LeaguesActivity", "Loading leagues for sport: $sportSlug")
        viewModel.loadTournaments(sportSlug)
    }

    private fun onLeagueClicked(league: TournamentEntity) {
        Log.d("LeaguesActivity", "League clicked: ${league.name}")
        val intent = Intent(this, LeagueDetailsActivity::class.java).apply {
            putExtra("leagueId", league.id)
            putExtra("leagueName", league.name)
        }
        startActivity(intent)
    }
}
