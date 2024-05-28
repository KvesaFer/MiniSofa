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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val _sport = MutableLiveData<String>("football")
    private val _date = MutableLiveData<String>(dateFormat.format(Date()))
    private val _events = MutableLiveData<List<EventInfo>>()

    val events: LiveData<List<EventInfo>> get() = _events

    fun setSport(sport: String) {
        Log.d("MainViewModel", "setSport called with $sport")
        _sport.value = sport
        fetchEvents()
    }

    fun setDate(date: String) {
        Log.d("MainViewModel", "setDate called with $date")
        _date.value = date
        fetchEvents()
    }

    private fun fetchEvents() {
        val sport = _sport.value ?: return
        val date = _date.value ?: return
        viewModelScope.launch {
            try {
                val events = repository.getEvents(sport, date)
                Log.d("MainViewModel", "Events from repo: ${events}")
                _events.value = events
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching events: ${e.message}")
            }
        }
    }
}