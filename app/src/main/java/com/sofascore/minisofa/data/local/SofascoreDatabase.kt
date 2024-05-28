package com.sofascore.minisofa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sofascore.minisofa.data.local.dao.CountryDao
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.dao.TournamentDao
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.data.local.entity.TournamentEntity

@Database(entities = [EventInfo::class, CountryEntity::class, TournamentEntity::class], version = 6)
abstract class SofascoreDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun countryDao(): CountryDao
    abstract fun tournamentDao(): TournamentDao
}
