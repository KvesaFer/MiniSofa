package com.sofascore.minisofa.data.repository

import com.sofascore.minisofa.data.local.dao.TournamentDao
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.remote.ApiService
import javax.inject.Inject

class TournamentRepository @Inject constructor(
    private val apiService: ApiService,
    private val tournamentDao: TournamentDao
) {
    suspend fun getTournamentDetails(tournamentId: Int): TournamentEntity? {
        val tournament = tournamentDao.getTournamentById(tournamentId)
        return tournament
    }
}