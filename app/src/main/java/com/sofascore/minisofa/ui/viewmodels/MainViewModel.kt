package com.sofascore.minisofa.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.launch

data class TournamentEvents(
    val tournamentName: String,
    val tournamentCountry: String,
    val tournamentLogo: String?,
    val events: List<EventInfo>
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val _sport = MutableLiveData("football")
    private val _date = MutableLiveData(dateFormat.format(Date()))
    private val _flatEventList = MutableLiveData<List<Any>>()

    val flatEventList: LiveData<List<Any>> get() = _flatEventList

    fun setSport(sport: String) {
        _sport.value = sport
        fetchEvents()
    }

    fun setDate(date: String) {
        _date.value = date
        fetchEvents()
    }

    private fun fetchEvents() {
        val sport = _sport.value ?: return
        val date = _date.value ?: return
        viewModelScope.launch {
            try {
                val events = repository.getEvents(sport, date)
                Log.d("MainViewModel", "Events: $events")

                val groupedEvents = events.groupBy { it.tournamentId }
                    .mapNotNull { (tournamentId, events) ->
                        val tournament = repository.getTournamentById(tournamentId) ?: return@mapNotNull null
                        val country = repository.getCountryById(tournament.countryId) ?: return@mapNotNull null
                        TournamentEvents(
                            tournamentName = tournament.name,
                            tournamentCountry = country.name,
                            tournamentLogo = tournament.logoUrl,
                            events = events.sortedBy { it.startTime }
                        )
                    }
                Log.d("MainViewModel", "Grouped Events: $groupedEvents")
                val flatList = mutableListOf<Any>()
                for (group in groupedEvents) {
                    flatList.add(group)
                    flatList.addAll(group.events)
                }
                _flatEventList.value = flatList
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error with grouping events: ${e.message}")
            }
        }
    }
}
