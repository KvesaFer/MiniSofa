package com.sofascore.minisofa.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _tournaments = MutableLiveData<List<TournamentEntity>>()
    val tournaments: LiveData<List<TournamentEntity>> get() = _tournaments

    fun loadTournaments(sportSlug: String) {
        Log.d("LeaguesViewModel", "Loading tournaments for sport: $sportSlug")
        viewModelScope.launch {
            try {
                val leagues = repository.getTournamentsBySport(sportSlug)
                Log.d("LeaguesViewModel", "Fetched ${leagues.size} tournaments from repository")
                _tournaments.postValue(leagues)
            } catch (e: Exception) {
                Log.e("LeaguesViewModel", "Error loading tournaments: ${e.message}", e)
            }
        }
    }
}
