package com.sofascore.minisofa.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _matches = MutableLiveData<List<Any>>()
    val matches: LiveData<List<Any>> get() = _matches

    private var currentUpcomingPage = 0
    private var currentPreviousPage = 0
    private var isLoading = false

    fun loadInitialMatches(tournamentId: Int) {
        if (isLoading) return
        isLoading = true
        Log.d("MatchesViewModel", "Starting to load initial matches for tournament ID: $tournamentId")

        viewModelScope.launch {
            try {
                val previousEvents = repository.getEventsForTournament(tournamentId, "last", currentPreviousPage)
                val nextEvents = repository.getEventsForTournament(tournamentId, "next", currentUpcomingPage)
                val events = (previousEvents + nextEvents).sortedBy { it.round }
                if (events.isNotEmpty()) {
                    currentPreviousPage++
                    currentUpcomingPage++
                    val groupedEvents = groupEventsByRound(events)
                    _matches.postValue(groupedEvents)
                    Log.d("MatchesViewModel", "Loaded initial events, total: ${groupedEvents.size}")
                }
            } catch (e: Exception) {
                Log.e("MatchesViewModel", "Error loading matches: ${e.message}", e)
            } finally {
                isLoading = false
                Log.d("MatchesViewModel", "Finished loading initial matches")
            }
        }
    }

    fun loadPreviousMatches(tournamentId: Int) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val previousEvents = repository.getEventsForTournament(tournamentId, "last", currentPreviousPage)
                if (previousEvents.isNotEmpty()) {
                    currentPreviousPage++
                    val currentList = _matches.value ?: emptyList()
                    val updatedList = groupEventsByRound((previousEvents + currentList.filterIsInstance<EventInfo>()).sortedBy { it.round })
                    _matches.postValue(updatedList)
                    Log.d("MatchesViewModel", "Loaded ${previousEvents.size} previous events, total: ${updatedList.size}")
                }
            } catch (e: Exception) {
                Log.e("MatchesViewModel", "Error loading previous matches: ${e.message}", e)
            } finally {
                isLoading = false
                Log.d("MatchesViewModel", "Finished loading previous matches")
            }
        }
    }

    fun loadUpcomingMatches(tournamentId: Int) {
        if (isLoading) return
        isLoading = true
        Log.d("MatchesViewModel", "Starting to load upcoming matches for tournament ID: $tournamentId, page: $currentUpcomingPage")

        viewModelScope.launch {
            try {
                val upcomingEvents = repository.getEventsForTournament(tournamentId, "next", currentUpcomingPage)
                if (upcomingEvents.isNotEmpty()) {
                    currentUpcomingPage++
                    val currentList = _matches.value ?: emptyList()
                    val updatedList = groupEventsByRound((currentList.filterIsInstance<EventInfo>() + upcomingEvents).sortedBy { it.round })
                    _matches.postValue(updatedList)
                    Log.d("MatchesViewModel", "Loaded ${upcomingEvents.size} upcoming events, total: ${updatedList.size}")
                }
            } catch (e: Exception) {
                Log.e("MatchesViewModel", "Error loading upcoming matches: ${e.message}", e)
            } finally {
                isLoading = false
                Log.d("MatchesViewModel", "Finished loading upcoming matches")
            }
        }
    }

    private fun groupEventsByRound(events: List<EventInfo>): List<Any> {
        return events.groupBy { it.round }.flatMap { (round, events) ->
            listOf("Round $round") + events
        }
    }
}