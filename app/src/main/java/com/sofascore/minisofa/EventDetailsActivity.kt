package com.sofascore.minisofa

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sofascore.minisofa.data.event_models.Incident
import com.sofascore.minisofa.data.event_models.MatchDetails
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.databinding.ActivityEventDetailsBinding
import com.sofascore.minisofa.databinding.EventScoreBinding
import com.sofascore.minisofa.databinding.UpcomingEventBinding
import com.sofascore.minisofa.ui.adapters.IncidentAdapter
import com.sofascore.minisofa.ui.viewmodels.EventDetailsViewModel
import com.sofascore.minisofa.utils.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EventDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailsBinding
    private val viewModel: EventDetailsViewModel by viewModels()
    private lateinit var scoreBinding: EventScoreBinding
    private lateinit var upcomingEventBinding: UpcomingEventBinding
    private lateinit var incidentAdapter: IncidentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.incidentRecyclerView.layoutManager = LinearLayoutManager(this)

        incidentAdapter = IncidentAdapter { incident ->
            val intent = Intent(this, PlayerDetailsActivity::class.java)
            intent.putExtra("PLAYER_ID", incident.player?.id)
            startActivity(intent)
        }

        binding.incidentRecyclerView.adapter = incidentAdapter

        val eventId = intent.getIntExtra("EVENT_ID", -1)
        Log.d("EventDetailsActivity", "Received eventId: $eventId")
        viewModel.fetchMatchDetails(eventId)

        setupObservers()
        startPeriodicUpdate(eventId)
    }

    private fun setupObservers() {
        viewModel.matchDetails.observe(this) { matchDetails ->
            Log.d("EventDetailsActivity", "Observed matchDetails: $matchDetails")
            matchDetails?.let { setupListeners(matchDetails.tournament) }
            matchDetails?.let { bindMatchDetails(it) }
        }
    }

    private fun setupListeners(tournament: TournamentEntity) {
        binding.backButton.setOnClickListener {
            Log.d("EventDetailsActivity", "Finished EventDetailsActivity")
            finish()
        }
        binding.leagueLogo.setOnClickListener{
            onLeagueClicked(tournament)
        }
        binding.twMatchDetailsTournament.setOnClickListener{
            onLeagueClicked(tournament)
        }

        binding.upcomingEventDesc.buttonTournamentDetails.setOnClickListener {
            onLeagueClicked(tournament)
        }
    }

    private fun bindMatchDetails(matchDetails: MatchDetails) {
        Log.d("EventDetailsActivity", "Binding match details: $matchDetails")
        val event = matchDetails.event
        val tournament = matchDetails.tournament
        val country = matchDetails.country

        binding.homeTeamName.text = event.homeName
        binding.awayTeamName.text = event.awayName
        binding.twMatchDetailsTournament.text = "${tournament.sport}, ${country.name}, ${tournament.name}, Round ${event.round}"

        Glide.with(this).load(event.homeTeamLogo).into(binding.homeTeamLogo)
        Glide.with(this).load(event.awayTeamLogo).into(binding.awayTeamLogo)
        Glide.with(this).load(tournament.logoUrl).into(binding.leagueLogo)
        when (event.status) {
            "notstarted" -> {
                Log.d("EventDetailsActivity", "Match status: notstarted")
                showUpcomingEvent(event)
            }
            "finished" -> {
                Log.d("EventDetailsActivity", "Match status: finished")
                showEventScore(event, true)
                matchDetails.incidents?.let { setupRecyclerView(it) }
            }
            else -> {
                Log.d("EventDetailsActivity", "Match status: in progress")
                showEventScore(event, false)
                matchDetails.incidents?.let { setupRecyclerView(it) }
            }
        }
    }

    private fun showUpcomingEvent(event: EventInfo) {
        Log.d("EventDetailsActivity", "Showing upcoming event: $event")
        upcomingEventBinding = UpcomingEventBinding.inflate(LayoutInflater.from(this), binding.scoreMatchDetails, true)
        Log.d("EventDetailsActivity", "Event startTime: ${event.startTime}, EventId: ${event.id}")

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = if (SettingsManager.isEUDateFormat(this)) {
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        } else {
            SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
        }
        val parsedDate = inputDateFormat.parse(event.date)
        val formattedDate = outputDateFormat.format(parsedDate)

        upcomingEventBinding.twMatchDate.text = formattedDate
        upcomingEventBinding.twMatchHour.text = event.startTime
        binding.upcomingEventDesc.notStartedEvent.visibility = View.VISIBLE
    }

    private fun showEventScore(event: EventInfo, isFinished: Boolean) {
        Log.d("EventDetailsActivity", "Showing event score: $event, isFinished: $isFinished")
        scoreBinding = EventScoreBinding.inflate(LayoutInflater.from(this), binding.scoreMatchDetails, true)
        scoreBinding.homeTeamScore.text = event.homeScore.toString()
        scoreBinding.awayTeamScore.text = event.awayScore.toString()

        val combinedDateTimeString = "${event.date}T${event.startTime}:00+00:00"
        val combinedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val combinedDateTime = combinedFormat.parse(combinedDateTimeString)

        val currentTime = Date()
        val minutesPlayed = combinedDateTime?.let {
            TimeUnit.MILLISECONDS.toMinutes(currentTime.time - it.time).toInt()
        } ?: 0

        scoreBinding.minutesPlayed.text = "$minutesPlayed'"

        binding.upcomingEventDesc.notStartedEvent.visibility = View.GONE


        if (isFinished) {
            val homeScore = event.homeScore
            val awayScore = event.awayScore
            Log.d("EventDetailsActivity", "Match is finished")
            scoreBinding.minutesPlayed.text = "Full Time"
            val lv2Color = getColor(R.color.on_surface_on_surface_lv_2)
            scoreBinding.minutesPlayed.setTextColor(lv2Color)


            if (homeScore == awayScore) {
                val drawColor = getColor(R.color.on_surface_on_surface_lv_2)
                scoreBinding.homeTeamScore.setTextColor(drawColor)
                scoreBinding.awayTeamScore.setTextColor(drawColor)
                scoreBinding.scoreSeparator.setTextColor(drawColor)
            } else if (homeScore > awayScore) {
                val winnerColor = getColor(R.color.on_surface_on_surface_lv_1)
                val loserColor = getColor(R.color.on_surface_on_surface_lv_2)
                scoreBinding.homeTeamScore.setTextColor(winnerColor)
                scoreBinding.awayTeamScore.setTextColor(loserColor)
                scoreBinding.scoreSeparator.setTextColor(loserColor)
            } else {
                val winnerColor = getColor(R.color.on_surface_on_surface_lv_1)
                val loserColor = getColor(R.color.on_surface_on_surface_lv_2)
                scoreBinding.homeTeamScore.setTextColor(loserColor)
                scoreBinding.awayTeamScore.setTextColor(winnerColor)
                scoreBinding.scoreSeparator.setTextColor(loserColor)
            }
        }
    }

    private fun setupRecyclerView(incidents: List<Incident>) {
        Log.d("EventDetailsActivity", "Setting up RecyclerView with incidents: $incidents")
        val sortedIncidents = incidents.sortedWith(compareByDescending<Incident> { it.time }
            .thenByDescending { it.type == "period" })
        incidentAdapter.submitList(sortedIncidents)
    }

    private fun startPeriodicUpdate(eventId: Int) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                viewModel.fetchMatchDetails(eventId)
                handler.postDelayed(this, 30000) // Update every 30 seconds
            }
        }
        handler.post(runnable)
    }

    private fun onLeagueClicked(league: TournamentEntity) {
        Log.d("LeaguesActivity", "League clicked: ${league.name}")
        val intent = Intent(this, LeagueDetailsActivity::class.java).apply {
            putExtra("leagueId", league.id)
            putExtra("leagueName", league.name)
            putExtra("SPORT_TYPE", league.sport)
        }
        startActivity(intent)
    }
}
