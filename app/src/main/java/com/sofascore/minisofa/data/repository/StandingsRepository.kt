package com.sofascore.minisofa.data.repository

import android.util.Log
import com.sofascore.minisofa.data.event_models.TeamEvent
import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.data.remote.ApiService
import com.sofascore.minisofa.utils.SportType
import javax.inject.Inject
class StandingsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getStandings(tournamentId: Int, sportType: SportType): List<TeamStanding> {
        val response = apiService.getStandings(tournamentId).filter { it.type == "total" }

        return response.flatMap { it.sortedStandingsRows.map { row ->
            when (sportType) {
                SportType.FOOTBALL -> TeamStanding(
                    id = row.team.id,
                    teamName = row.team.name,
                    played = row.played,
                    won = row.wins,
                    draw = row.draws,
                    lost = row.losses,
                    goals = "${row.scoresFor}:${row.scoresAgainst}",
                    points = row.points,
                    percentage = null,
                    diff = null,
                    streak = null
                )
                SportType.BASKETBALL -> TeamStanding(
                    id = row.team.id,
                    teamName = row.team.name,
                    played = row.played,
                    won = row.wins,
                    draw = 0,
                    lost = row.losses,
                    goals = null,
                    points = 0,
                    percentage = row.percentage,
                    diff = row.scoresFor - row.scoresAgainst,
                    streak = null
                )
                SportType.AMERICAN_FOOTBALL -> TeamStanding(
                    id = row.team.id,
                    teamName = row.team.name,
                    played = row.played,
                    won = row.wins,
                    draw = row.draws,
                    lost = row.losses,
                    goals = null,
                    points = 0,
                    percentage = row.percentage,
                    diff = null,
                    streak = null
                )
            }
        }}
    }

    suspend fun getTeamEvents(teamId: Int, span: String, page: Int): List<TeamEvent> {
        return apiService.getTeamEvents(teamId, span, page)
    }

    suspend fun fetchTeamStreak(teamId: Int): Int {
        try {
            val events = getTeamEvents(teamId, "last", 1)
            val sortedEvents = events.sortedByDescending { it.startDate }
            var streak = 0

            for (event in sortedEvents) {
                val isWin = if (event.homeTeam.id == teamId) event.homeScore.total > event.awayScore.total else event.awayScore.total > event.homeScore.total

                if (isWin) {
                    streak++
                } else {
                    break
                }
            }
            return streak
        } catch (e: Exception) {
            Log.e("StandingsRepository", "Error fetching team streak: ${e.message}", e)
            throw e
        }
    }
}