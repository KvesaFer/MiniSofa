package com.sofascore.minisofa.data.repository

import com.sofascore.minisofa.data.event_models.EventApiResponse
import com.sofascore.minisofa.data.event_models.PlayerDetails
import com.sofascore.minisofa.data.remote.ApiService
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPlayerMatches(playerId: Int, span: String, page: Int): List<EventApiResponse> {
        return apiService.getPlayerEvents(playerId, span, page)
    }

    suspend fun getPlayerInfo(playerId: Int): PlayerDetails {
        return apiService.getPlayerDetails(playerId)
    }
}