package com.sofascore.minisofa.data.repository

import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.data.remote.ApiService
import javax.inject.Inject

class StandingsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getStandings(tournamentId: Int): List<TeamStanding> {
        val response = apiService.getStandings(tournamentId).filter { it.type == "total" }

        return response.flatMap { it.sortedStandingsRows.map { row ->
            TeamStanding(
                id = row.id,
                teamName = row.team.name,
                played = row.played,
                won = row.wins,
                draw = row.draws,
                lost = row.losses,
                goals = "${row.scoresFor}:${row.scoresAgainst}",
                points = row.points
            )
        }}
    }
}