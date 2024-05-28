package com.sofascore.minisofa.data.repository

import android.util.Log
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.remote.ApiService
import java.util.Locale
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
){
    suspend fun getEvents(sportSlug: String, date: String): List<EventInfo> {
        Log.d("EventRepository", "getEvents called with sportSlug: $sportSlug, date: $date")
        val eventsAPI = apiService.getEvents(sportSlug, date).map { it.toEventInfo() }
        val normalizedEvents = eventsAPI.map { it.copy(sport = it.sport.lowercase(Locale.ROOT))}
        Log.d("EventRepository", "Fetched events from apiService: $normalizedEvents")
        eventDao.insertAll(normalizedEvents)
        Log.d("EventRepository", "Fetched events from apiService: ${eventDao.getEventsByDateAndSport(date, sportSlug)}")
        val events = eventDao.getEventsByDateAndSport(date, sportSlug)
        for (event in events) {
            val responseLogo = apiService.getTournamentLogo(event.tournamentId)
            if (responseLogo.isSuccessful) {
                event.tournamentLogo = responseLogo.raw().request.url.toString()
                Log.d("EventRepository", "Response for getTournamentLogo [RAW]: ${responseLogo.raw().request.url}")
            }

            val responseTournament = apiService.getTournamentDetails(event.tournamentId)
            Log.d("EventRepository", "Response for getTournamentDetails [RAW]: ${responseTournament.country}")

            event.tournamentCountry = responseTournament.country.name
        }
        return events
    }
}