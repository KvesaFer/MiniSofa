package com.sofascore.minisofa

import android.widget.TextView
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.sofascore.minisofa.data.event_models.Country
import com.sofascore.minisofa.data.event_models.Tournament
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.databinding.ActivityLeagueDetailsBinding
import com.sofascore.minisofa.ui.LeagueDetailsViewModel
import com.sofascore.minisofa.utils.CountryCodes
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LeagueDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeagueDetailsBinding
    private val viewModel: LeagueDetailsViewModel by viewModels()
    private val countryCodes = CountryCodes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LeagueDetailsActivity", "onCreate called")
        binding = ActivityLeagueDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupViewPagerAndTabs()

        val tournamentId = intent.getIntExtra("leagueId", -1)
        if (tournamentId != -1) {
            viewModel.fetchTournamentDetails(tournamentId)
        }

        viewModel.tournamentDetails.observe(this) { details ->
            details?.let {
                updateToolbar(it)
            }
        }

        viewModel.country.observe(this) { country ->
            country?.let {
                updateCountryFlag(it)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar.root.findViewById(R.id.toolbar_for_league))
        val backButton: ImageView = binding.toolbar.root.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            Log.d("LeagueDetailsActivity", "Back button clicked")
            finish()
        }
    }

    private fun setupViewPagerAndTabs() {
        val pagerAdapter = ViewPagerAdapter(this, intent.getIntExtra("leagueId", -1))
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Matches"
                1 -> "Standings"
                else -> null
            }
        }.attach()
    }

    private fun updateToolbar(details: TournamentEntity) {
        val leagueName: TextView = binding.toolbar.root.findViewById(R.id.leagueName)
        val leagueLogo: ImageView = binding.toolbar.root.findViewById(R.id.leagueLogo)

        leagueName.text = details.name

        Glide.with(this)
            .load(details.logoUrl)
            .into(leagueLogo)
    }

    private fun updateCountryFlag(country: CountryEntity) {
        val leagueCountryLogo: ImageView = binding.toolbar.root.findViewById(R.id.leagueCountryLogo)
        val leagueCountry: TextView = binding.toolbar.root.findViewById(R.id.leagueCountry)
        val countryFlagUrl = getCountryFlagUrlByName(country.name)

        leagueCountry.text = country.name
        Glide.with(this)
            .load(countryFlagUrl)
            .into(leagueCountryLogo)
    }

    private fun getCountryFlagUrlByName(countryName: String): String {
        val countryCode = countryCodes.getCountryCode(countryName)
        Log.d("LeagueDetailsActivity", "Country Code: $countryCode, Country name: $countryName")
        return "https://flagsapi.com/$countryCode/flat/64"
    }

    private inner class ViewPagerAdapter(
        fa: FragmentActivity,
        private val tournamentId: Int
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MatchesFragment().apply {
                    arguments = Bundle().apply {
                        putInt("TOURNAMENT_ID", tournamentId)
                    }
                }
                1 -> StandingsFragment().apply {
                    arguments = Bundle().apply {
                        putInt("TOURNAMENT_ID", tournamentId)
                    }
                }
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
