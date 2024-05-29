package com.sofascore.minisofa.data.repository

import android.util.Log
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.sofascore.minisofa.data.local.dao.CountryDao
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.dao.TournamentDao
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.remote.ApiService
import java.util.Locale
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val countryDao: CountryDao,
    private val tournamentDao: TournamentDao
) {
    suspend fun getEvents(sportSlug: String, date: String): List<EventInfo> {
        Log.d("EventRepository", "getEvents called with sportSlug: $sportSlug, date: $date")

        // Fetch events from API
        val eventsAPI = try {
            apiService.getEvents(sportSlug, date)
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching events from API: ${e.message}")
            return emptyList()
        }
        Log.d("EventRepository", "Fetched ${eventsAPI.size} events from API")

        // Normalize and map events to EventInfo
        val normalizedEvents = eventsAPI.map { apiResponse ->
            val eventInfo = apiResponse.toEventInfo()
            Log.d("EventRepository", "Mapped EventApiResponse to EventInfo: $eventInfo")

            // Ensure countries and tournaments are inserted into the database
            val countries = listOf(apiResponse.homeTeam.country, apiResponse.awayTeam.country, apiResponse.tournament.country)
                .distinctBy { it.id }
                .map { CountryEntity(it.id, it.name) }
            countryDao.insertAll(countries)
            Log.d("EventRepository", "Inserted countries into database: $countries")

            val tournament = TournamentEntity(
                id = apiResponse.tournament.id,
                name = apiResponse.tournament.name,
                slug = apiResponse.tournament.slug,
                sport = apiResponse.tournament.sport.name,
                countryId = apiResponse.tournament.country.id,
                logoUrl = "https://academy-backend.sofascore.dev/tournament/${apiResponse.tournament.id}/image"
            )
            tournamentDao.insertAll(listOf(tournament))
            Log.d("EventRepository", "Inserted tournament into database: $tournament")

            eventInfo
        }

        // Insert events into the database
        eventDao.insertAll(normalizedEvents)
        Log.d("EventRepository", "Inserted ${normalizedEvents.size} events into the database")

        // Fetch events from the database
        val eventsFromDb = eventDao.getEventsByDateAndSport(
            date,
            sportSlug.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
        Log.d("EventRepository", "Fetched ${eventsFromDb.size} events from the database")

        return eventsFromDb
    }

    suspend fun getTournamentById(tournamentId: Int): TournamentEntity? {
        return tournamentDao.getTournamentById(tournamentId)
    }

    suspend fun getCountryById(countryId: Int): CountryEntity? {
        return countryDao.getCountryById(countryId)
    }

    suspend fun getTournamentsBySport(sportSlug: String): List<TournamentEntity> {
        val leagues = try {
            apiService.getTournamentsBySport(sportSlug)
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching tournaments from API: ${e.message}")
            return emptyList()
        }
        val leagues_db = leagues.map {
            TournamentEntity(
                id = it.id,
                name = it.name,
                slug = it.slug,
                sport = it.sport.name,
                countryId = it.country.id,
                logoUrl = "https://academy-backend.sofascore.dev/tournament/${it.id}/image"
            )
        }
        tournamentDao.insertAll(leagues_db)
        return tournamentDao.getTournamentsBySport(sportSlug.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
    }

    suspend fun getEventsForTournament(tournamentId: Int, span: String, page: Int): List<EventInfo> {
        return try {
            val eventsApiResponse = apiService.getTournamentEvents(tournamentId, span, page)
            val events = eventsApiResponse.map { it.toEventInfo() }
            eventDao.insertAll(events)
            events
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching events: ${e.message}", e)
            emptyList()
        }
    }
}