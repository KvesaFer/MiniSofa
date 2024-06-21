package com.sofascore.minisofa.data.remote

import com.sofascore.minisofa.data.event_models.EventApiResponse
import com.sofascore.minisofa.data.event_models.Incident
import com.sofascore.minisofa.data.event_models.PlayerDetails
import com.sofascore.minisofa.data.event_models.StandingsResponse
import com.sofascore.minisofa.data.event_models.TeamEvent
import com.sofascore.minisofa.data.event_models.Tournament
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("sport/{slug}/events/{date}")
    suspend fun getEvents(
        @Path("slug") sportSlug: String,
        @Path("date") date: String
    ): List<EventApiResponse>


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

    @GET("team/{id}/events/{span}/{page}")
    suspend fun getTeamEvents(
        @Path("id") id: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<TeamEvent>

    @GET("event/{id}")
    suspend fun getEventByEventId(@Path("id") id: Int): EventApiResponse

    @GET("event/{id}/incidents")
    suspend fun getEventIncidents(@Path("id") id: Int): List<Incident>

    @GET("player/{id}/events/{span}/{page}")
    suspend fun getPlayerEvents(
        @Path("id") id: Int,
        @Path("span") span: String,
        @Path("page") page: Int
    ): List<EventApiResponse>

    @GET("player/{id}")
    suspend fun getPlayerDetails(@Path("id") id: Int): PlayerDetails

}
