package com.sofascore.minisofa.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.remote.ApiService
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class EventRepository @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
){
    suspend fun getEvents(sportSlug: String, date: String): List<EventInfo> {
        Log.d("EventRepository", "getEvents called with sportSlug: $sportSlug, date: $date")
        val events = apiService.getEvents(sportSlug, date).map { it.toEventInfo() }
        val normalizedEvents = events.map { it.copy(sport = it.sport.lowercase(Locale.ROOT))}
        Log.d("EventRepository", "Fetched events from apiService: $normalizedEvents")
        eventDao.insertAll(normalizedEvents)
        Log.d("EventRepository", "Fetched events from apiService: ${eventDao.getEventsByDateAndSport(date, sportSlug)}")
        return eventDao.getEventsByDateAndSport(date, sportSlug)
    }
}