package com.sofascore.minisofa.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.event_models.PlayerDetails
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@HiltViewModel
class PlayerDetailsViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _playerMatches = MutableLiveData<List<EventInfo>>()
    private val _playerDetails = MutableLiveData<PlayerDetails>()
    val playerMatches: LiveData<List<EventInfo>> get() = _playerMatches
    val playerDetails: LiveData<PlayerDetails> get() = _playerDetails

//    fun fetchPlayerMatches(playerId: Int, span: String, page: Int) {
//        viewModelScope.launch {
//            val apiResponseMatches = playerRepository.getPlayerMatches(playerId, span, page)
//            val matches = apiResponseMatches.map { it.toEventInfo() }
//            _playerMatches.value = matches
//        }
//    }

    fun fetchMultiplePlayerMatches(playerId: Int) {
        viewModelScope.launch {
            val apiResponseMatches = listOf(
                async { playerRepository.getPlayerMatches(playerId, "last", 1) },
                async { playerRepository.getPlayerMatches(playerId, "next", 1) },
                async { playerRepository.getPlayerMatches(playerId, "next", 2) },
                async { playerRepository.getPlayerMatches(playerId, "next", 3) }
            ).awaitAll().flatten()
            val matches = apiResponseMatches.map { it.toEventInfo() }
            _playerMatches.value = matches
        }
    }

    fun fetchPlayerInformation(playerId: Int) {
        viewModelScope.launch {
            val player = playerRepository.getPlayerInfo(playerId)
            player.clubImage = "https://academy-backend.sofascore.dev/team/${player.team.id}/image"
            player.image = "https://academy-backend.sofascore.dev/player/${playerId}/image"
            _playerDetails.postValue(player)
        }
    }
}
