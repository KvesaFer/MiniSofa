package com.sofascore.minisofa.data.repository

import android.util.Log
import com.sofascore.minisofa.data.event_models.MatchDetails
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.sofascore.minisofa.data.local.dao.CountryDao
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.dao.TournamentDao
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.remote.ApiService
import com.sofascore.minisofa.utils.SportType
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val countryDao: CountryDao,
    private val tournamentDao: TournamentDao
) {
    suspend fun getEvents(sportType: SportType, date: String): List<EventInfo> {
        Log.d("EventRepository", "getEvents called with sportSlug: ${sportType.apiName}, date: $date")

        val eventsAPI = try {
            apiService.getEvents(sportType.apiName, date)
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching events from API: ${e.message}")
            return emptyList()
        }
        Log.d("EventRepository", "Fetched ${eventsAPI.size} events from API")

        val normalizedEvents = eventsAPI.map { apiResponse ->
            val eventInfo = apiResponse.toEventInfo()
            Log.d("EventRepository", "Mapped EventApiResponse to EventInfo: $eventInfo")

            eventInfo
        }

            val countries = normalizedEvents.flatMap { listOf(it.homeTeamCountryId, it.awayTeamCountryId) }
                .distinct()
                .mapNotNull { countryId ->
                    eventsAPI.find { it.homeTeam.country.id == countryId || it.awayTeam.country.id == countryId }
                        ?.let { CountryEntity(countryId, it.homeTeam.country.name) }
                }
            countryDao.insertAll(countries)
            Log.d("EventRepository", "Inserted countries into database: $countries")

            val tournaments = eventsAPI.map { apiResponse ->
                TournamentEntity(
                    id = apiResponse.tournament.id,
                    name = apiResponse.tournament.name,
                    slug = apiResponse.tournament.slug,
                    sport = apiResponse.tournament.sport.name,
                    countryId = apiResponse.tournament.country.id,
                    logoUrl = "https://academy-backend.sofascore.dev/tournament/${apiResponse.tournament.id}/image"
                )
            }
            tournamentDao.insertAll(tournaments)
            Log.d("EventRepository", "Inserted tournaments into database: $tournaments")

            eventDao.insertAll(normalizedEvents)
            Log.d("EventRepository", "Inserted ${normalizedEvents.size} events into the database")

        val eventsFromDb = eventDao.getEventsByDateAndSport(
            date,
            sportType.displayName)
        Log.d("EventRepository", "Fetched ${eventsFromDb.size} events from the database")

        return eventsFromDb
    }

    suspend fun getTournamentById(tournamentId: Int): TournamentEntity? {
        return tournamentDao.getTournamentById(tournamentId)
    }

    suspend fun getCountryById(countryId: Int): CountryEntity? {
        return countryDao.getCountryById(countryId)
    }

    suspend fun getTournamentsBySport(sportType: SportType): List<TournamentEntity> {
        val leagues = try {
            apiService.getTournamentsBySport(sportType.apiName)
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
        return tournamentDao.getTournamentsBySport(sportType.displayName)
    }


    suspend fun getMatchDetails(eventId: Int): MatchDetails? {
        val event = apiService.getEventByEventId(eventId).toEventInfo()
        val tournament = event.tournamentId?.let { tournamentDao.getTournamentById(it) }
        val country = tournament?.let { countryDao.getCountryById(it.countryId) }
        val incidents = apiService.getEventIncidents(eventId)
        incidents.forEach { incident ->
            incident.sport = event.sport
        }

        return country?.let {
            MatchDetails(
                event = event,
                country = it,
                tournament = tournament,
                incidents = incidents
            )
        }
    }


    suspend fun getEventsForTournament(tournamentId: Int, span: String, page: Int): List<EventInfo> {
        return try {
            val eventsApiResponse = apiService.getTournamentEvents(tournamentId, span, page)
            Log.d("EventRepository", "EventApiResponse: $eventsApiResponse")

            val countries = eventsApiResponse.flatMap { listOf(it.homeTeam.country, it.awayTeam.country) }.distinct()
            countries.forEach { country ->
                val countryEntity = CountryEntity(id = country.id, name = country.name)
                countryDao.insert(countryEntity)
            }
            val events = eventsApiResponse.map { it.toEventInfo() }
            eventDao.insertAll(events)
            events
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching events: ${e.message}", e)
            emptyList()
        }
    }
}
