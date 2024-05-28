package com.sofascore.minisofa.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sofascore.minisofa.data.local.entity.EventInfo

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE date(date) = :date AND sport = :sport")
    suspend fun getEventsByDateAndSport(date: String, sport: String): List<EventInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventInfo>)

    @Query("DELETE FROM events WHERE date = :date AND sport = :sport")
    suspend fun deleteEventsByDateAndSport(date: String, sport: String)
}
