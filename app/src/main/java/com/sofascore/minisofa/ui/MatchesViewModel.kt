package com.sofascore.minisofa.ui

import android.provider.CalendarContract.EventsEntity
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

    private var currentPage = 0
    private var isLoading = false

    companion object {
        private const val TAG = "MatchesViewModel"
    }

    fun loadMatches(tournamentId: Int) {
        if (isLoading) {
            Log.d(TAG, "Already loading matches, skipping request")
            return
        }
        isLoading = true
        Log.d(TAG, "Starting to load matches for tournament ID: $tournamentId, page: $currentPage")

        viewModelScope.launch {
            try {
                val previousEvents = repository.getEventsForTournament(tournamentId, "last", currentPage)
                val events = repository.getEventsForTournament(tournamentId, "next", currentPage)
                if (events.isNotEmpty()) {
                    currentPage++
                    val currentList = _matches.value ?: emptyList()
                    val groupedEvents = groupEventsByRound(currentList, events)
                    _matches.postValue(groupedEvents)
                    Log.d(TAG, "Loaded ${events.size} events, total: ${groupedEvents.size}")
                } else {
                    Log.d(TAG, "No more events to load")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading matches: ${e.message}", e)
            } finally {
                isLoading = false
                Log.d(TAG, "Finished loading matches")
            }
        }
    }

    private fun groupEventsByRound(currentList: List<Any>, events: List<EventInfo>): List<Any> {
        return currentList + events.groupBy { it.round }.flatMap { (round, events) ->
            listOf("Round $round") + events
        }
    }
}
