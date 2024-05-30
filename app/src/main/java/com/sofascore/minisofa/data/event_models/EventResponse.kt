package com.sofascore.minisofa.data.event_models

import com.sofascore.minisofa.data.local.entity.EventInfo


data class EventApiResponse(
    val id: Int,
    val slug: String,
    val tournament: Tournament,
    val homeTeam: Team,
    val awayTeam: Team,
    val status: String,
    val startDate: String,
    val homeScore: Score,
    val awayScore: Score,
    val winnerCode: String?,
    val round: Int,
    val startTime: String,
    var tournamentLogo: String,
    var tournamentCountry: String
)

data class StandingsResponse(
    val id: Int,
    val tournament: Tournament,
    val type: String,
    val sortedStandingsRows: List<StandingRow>
)

data class StandingRow(
    val id: Int,
    val team: Team,
    val points: Int,
    val scoresFor: Int,
    val scoresAgainst: Int,
    val played: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int
)

data class Tournament(
    val id: Int,
    val name: String,
    val slug: String,
    val sport: Sport,
    val country: Country
)

data class Team(
    val id: Int,
    val name: String,
    val country: Country
)

data class Sport(
    val id: Int,
    val name: String,
    val slug: String
)

data class Country(
    val id: Int,
    val name: String
)

data class Score(
    val total: Int? = null,
    val period2: Int? = null
)

data class EventResponse(
    val events: List<EventApiResponse>
)

data class TeamStanding(
    val id: Int,
    var position:Int = 0,
    var teamName: String,
    val played: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val goals: String,
    val points: Int
)


fun EventApiResponse.toEventInfo(): EventInfo {
    return EventInfo(
        id = this.id,
        date = this.startDate.split("T")[0],
        homeName = this.homeTeam.name,
        awayName = this.awayTeam.name,
        homeScore = this.homeScore.total ?: 0,
        awayScore = this.awayScore.total ?: 0,
        status = this.status,
        sport = this.tournament.sport.name,
        tournamentId = this.tournament.id,
        homeTeamCountryId = this.homeTeam.country.id,
        awayTeamCountryId = this.awayTeam.country.id,
        homeTeamLogo = "https://academy-backend.sofascore.dev/team/${this.homeTeam.id}/image",
        awayTeamLogo = "https://academy-backend.sofascore.dev/team/${this.awayTeam.id}/image",
        startTime = this.startDate.split("T")[1].split("+")[0].substring(startIndex = 0, endIndex = 5),
        round = this.round
    )
}