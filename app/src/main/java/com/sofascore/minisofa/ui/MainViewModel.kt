package com.sofascore.minisofa.ui

import android.media.metrics.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
                val groupedEvents = events.groupBy { it.tournamentName }
                    .map { (tournamentName, events) ->
                        TournamentEvents(
                            tournamentName = tournamentName,
                            tournamentCountry = events.first().tournamentCountry,
                            tournamentLogo = events.first().tournamentLogo,
                            events = events.sortedBy { it.startTime }
                        )
                    }
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
