package com.sofascore.minisofa.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.repository.EventRepository
import com.sofascore.minisofa.utils.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _tournaments = MutableLiveData<List<TournamentEntity>>()
    private val _sportType = MutableLiveData<SportType>()
    val tournaments: LiveData<List<TournamentEntity>> get() = _tournaments
    val sportType: MutableLiveData<SportType> get() = _sportType


    fun loadTournaments(sportType: SportType) {
        Log.d("LeaguesViewModel", "Loading tournaments for sport: ${sportType.apiName}")
        viewModelScope.launch {
            try {
                val leagues = repository.getTournamentsBySport(sportType)
                Log.d("LeaguesViewModel", "Fetched ${leagues.size} tournaments from repository")
                _tournaments.postValue(leagues)
                _sportType.postValue(sportType)
            } catch (e: Exception) {
                Log.e("LeaguesViewModel", "Error loading tournaments: ${e.message}", e)
            }
        }
    }
}
