package com.sofascore.minisofa.data.remote

import com.sofascore.minisofa.data.event_models.EventApiResponse
import com.sofascore.minisofa.data.event_models.EventResponse
//import com.sofascore.minisofa.data.event_models.Incident
//import com.sofascore.minisofa.data.event_models.Player
//import com.sofascore.minisofa.data.event_models.PlayerDetails
//import com.sofascore.minisofa.data.event_models.Standing
//import com.sofascore.minisofa.data.event_models.TeamDetails
import com.sofascore.minisofa.data.event_models.Tournament
//import com.sofascore.minisofa.data.event_models.TournamentDetails
import com.sofascore.minisofa.data.local.entity.EventInfo
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("sport/{slug}/events/{date}")
    suspend fun getEvents(
        @Path("slug") sportSlug: String,
        @Path("date") date: String
    ): List<EventApiResponse>

    @GET("sport/{slug}/tournaments")
    suspend fun getTournaments(
        @Path("slug") sportSlug: String
    ): List<Tournament>



//    @GET("event/{id}")
//    suspend fun getEventDetails(
//        @Path("id") eventId: Int
//    ): EventDetails

//    @GET("event/{id}/incidents")
//    suspend fun getEventIncidents(
//        @Path("id") eventId: Int
//    ): List<Incident>

//    @GET("team/{id}")
//    suspend fun getTeamDetails(
//        @Path("id") teamId: Int
//    ): TeamDetails

    @GET("team/{id}/events/{span}/{page}")
    suspend fun getTeamEvents(
        @Path("id") teamId: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<EventInfo>

//    @GET("team/{id}/players")
//    suspend fun getTeamPlayers(
//        @Path("id") teamId: Int
//    ): List<Player>

    @GET("team/tournaments")
    suspend fun getTeamTournaments(): List<Tournament>

    @GET("team/{id}/image")
    suspend fun getTeamLogo(
        @Path("id") teamId: Int
    ): retrofit2.Response<Void>

    @GET("tournament/{id}/image")
    suspend fun getTournamentLogo(
        @Path("id") tournamentId: Int
    ): retrofit2.Response<Void>

    @GET("tournament/{id}")
    suspend fun getTournamentDetails(
        @Path("id") tournamentId: Int
    ): Tournament

//    @GET("tournament/{id}/standings")
//    suspend fun getTournamentStandings(
//        @Path("id") tournamentId: Int
//    ): List<Standing>

    @GET("tournament/{id}/events/{span}/{page}")
    suspend fun getTournamentEvents(
        @Path("id") tournamentId: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<EventInfo>

//    @GET("player/{id}")
//    suspend fun getPlayerDetails(
//        @Path("id") playerId: Int
//    ): PlayerDetails

    @GET("player/{id}/events/{span}/{page}")
    suspend fun getPlayerEvents(
        @Path("id") playerId: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<EventInfo>
}