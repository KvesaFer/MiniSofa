package com.sofascore.minisofa.ui.viewmodels

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
                val standings = repository.getStandings(tournamentId).sortedByDescending { it.points }
                var ctr = 1
                for (row in standings) {
                    row.teamName = shortenTeamName(row.teamName)
                    row.position = ctr
                    ctr++
                }
                Log.d("StandingsViewModel", "Fteched standings: $standings")
                _standings.postValue(standings)
            } catch (e: Exception) {
                Log.e("StandingsViewModel", "Error loading standings: ${e.message}", e)
            }
        }
    }

    private fun shortenTeamName(teamName: String): String {
        return if (teamName.length > 12) {
            val parts = teamName.split(" ")
            if (parts.size < 2) {
                return teamName.substring(0,10) + "."
            }
            val shortenedName = StringBuilder()

            for (i in 0 until parts.size - 1) {
                if (parts[i].length > 3) {
                    shortenedName.append(parts[i].substring(0, 3)).append(". ")
                } else {
                    shortenedName.append(parts[i]).append(" ")
                }
            }

            shortenedName.append(parts.last())
            shortenedName.toString()
        } else {
            teamName
        }
    }

}