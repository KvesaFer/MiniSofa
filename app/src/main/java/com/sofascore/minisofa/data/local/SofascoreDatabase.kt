package com.sofascore.minisofa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.entity.EventInfo

@Database(entities = [EventInfo::class], version = 3)
abstract class SofascoreDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
