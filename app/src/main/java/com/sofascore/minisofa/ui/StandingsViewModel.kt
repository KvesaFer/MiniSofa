package com.sofascore.minisofa.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.data.repository.StandingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val repository: StandingsRepository
) : ViewModel() {

    private val _standings = MutableLiveData<List<TeamStanding>>()
    val standings: LiveData<List<TeamStanding>> get() = _standings

    fun loadStandings(tournamentId: Int) {
        viewModelScope.launch {
            try {
                val standings = repository.getStandings(tournamentId)
                _standings.postValue(standings)
            } catch (e: Exception) {
                Log.e("StandingsViewModel", "Error loading standings: ${e.message}", e)
            }
        }
    }
}