package com.sofascore.minisofa.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.event_models.MatchDetails
import com.sofascore.minisofa.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _matchDetails = MutableLiveData<MatchDetails?>()
    val matchDetails: LiveData<MatchDetails?> get() = _matchDetails

    fun fetchMatchDetails(eventId: Int) {
        viewModelScope.launch {
            val matchDetails = eventRepository.getMatchDetails(eventId)
            _matchDetails.postValue(matchDetails)
        }
    }
}