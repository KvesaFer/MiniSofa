package com.sofascore.minisofa.data.remote

import com.sofascore.minisofa.data.event_models.EventApiResponse
import com.sofascore.minisofa.data.event_models.EventResponse
import com.sofascore.minisofa.data.event_models.StandingsResponse
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

    @GET("team/{id}/image")
    suspend fun getTeamLogo(
        @Path("id") teamId: Int
    ): retrofit2.Response<Void>

    @GET("tournament/{id}/image")
    suspend fun getTournamentLogo(
        @Path("id") tournamentId: Int
    ): String

    @GET("tournament/{id}")
    suspend fun getTournamentDetails(
        @Path("id") tournamentId: Int
    ): Tournament

    @GET("sport/{slug}/tournaments")
    suspend fun getTournamentsBySport(
        @Path("slug") slug: String
    ): List<Tournament>

    @GET("tournament/{id}/events/{span}/{page}")
    suspend fun getTournamentEvents(
        @Path("id") tournamentId: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<EventApiResponse>

    @GET("tournament/{id}/standings")
    suspend fun getStandings(@Path("id") tournamentId: Int): List<StandingsResponse>

}
