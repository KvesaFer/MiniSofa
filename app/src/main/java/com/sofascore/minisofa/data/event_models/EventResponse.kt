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
    val total: Int,
    val period2: Int
)

data class EventResponse(
    val events: List<EventApiResponse>
)

fun EventApiResponse.toEventInfo(): EventInfo {
    return EventInfo(
        id = this.id,
        date = this.startDate.split("T")[0],
        homeName = this.homeTeam.name,
        awayName = this.awayTeam.name,
        homeScore = this.homeScore.total,
        awayScore = this.awayScore.total,
        status = this.status,
        sport = this.tournament.sport.name,
        tournamentId = this.tournament.id,
        tournamentName = this.tournament.name,
        homeTeamCountry = this.homeTeam.country.name,
        awayTeamCountry = this.awayTeam.country.name,
        homeTeamLogo = "https://academy-backend.sofascore.dev/team/${this.homeTeam.id}/image",
        awayTeamLogo = "https://academy-backend.sofascore.dev/team/${this.awayTeam.id}/image",
        startTime = this.startDate.split("T")[1].split("+")[0].substring(startIndex = 0, endIndex = 5),
        tournamentLogo = "",
        tournamentCountry = ""
    )
}