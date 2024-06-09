package com.sofascore.minisofa

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofascore.minisofa.databinding.ActivityPlayerDetailsBinding
import com.sofascore.minisofa.utils.SettingsManager
import com.sofascore.minisofa.ui.adapters.MatchesAdapter
import com.sofascore.minisofa.ui.viewmodels.PlayerDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.sofascore.minisofa.data.event_models.PlayerDetails
import com.sofascore.minisofa.utils.CountryCodes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@AndroidEntryPoint
class PlayerDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerDetailsBinding
    private val viewModel: PlayerDetailsViewModel by viewModels()
    private lateinit var matchesAdapter: MatchesAdapter
    private val countryCodes = CountryCodes()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (SettingsManager.isDarkTheme(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupRecyclerView()
        setupObservers()

        val playerId = intent.getIntExtra("PLAYER_ID", -1)
        viewModel.fetchPlayerInformation(playerId)
        viewModel.fetchMultiplePlayerMatches(playerId)
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.headerPlayerDetails.root.findViewById(R.id.toolbarPlayerDetails))
        binding.headerPlayerDetails.backButton.setOnClickListener {
            Log.d("PlayerDetailsActivity", "Back button clicked")
            finish()
        }
    }

    private fun setupRecyclerView() {
        matchesAdapter = MatchesAdapter()
        binding.playerMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupObservers() {
        viewModel.playerDetails.observe(this) {playerDetails ->
            Log.d("PlayerDetailsActivity", "Observed playerDetails: $playerDetails")
            bindPlayerDetails(playerDetails)
        }

        viewModel.playerMatches.observe(this) { matches ->
            Log.d("PlayerDetailsActivity", "Observed matches: $matches")
            matches?.let {
                matchesAdapter.submitList(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindPlayerDetails (playerDetails: PlayerDetails){
        Glide.with(this).load(playerDetails.image).into(binding.headerPlayerDetails.playerLogo)
        binding.headerPlayerDetails.playerName.text = playerDetails.name
        Glide.with(this).load(playerDetails.clubImage).into(binding.clubLogo)
        binding.clubName.text = playerDetails.team.name

        binding.countryName.text = countryCodes.getCountryCode(playerDetails.country.name)
        Glide.with(this).load(getCountryFlagUrlByName(playerDetails.country.name)).into(binding.countryFlag)

        val position = when(playerDetails.position) {
            "M" -> "Midfield"
            "F" -> "Forward"
            "D" -> "Defence"
            else -> "Goalkeeper"
        }
        binding.playerPosition.text = position

        val dateOfBirth = LocalDate.parse(playerDetails.dateOfBirth, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val formattedDate = dateOfBirth.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault()))
        binding.twBirthdayDate.text = formattedDate

        val currentDate = LocalDate.now()
        val age = ChronoUnit.YEARS.between(dateOfBirth, currentDate).toInt()
        binding.numberOfYears.text = "$age Yrs"
    }

    private fun getCountryFlagUrlByName(countryName: String): String {
        val countryCode = countryCodes.getCountryCode(countryName)
        Log.d("LeagueDetailsActivity", "Country Code: $countryCode, Country name: $countryName")
        return "https://flagsapi.com/$countryCode/flat/64"
    }
}
