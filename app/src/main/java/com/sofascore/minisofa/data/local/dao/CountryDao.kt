package com.sofascore.minisofa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sofascore.minisofa.data.local.entity.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries WHERE id = :countryId")
    suspend fun getCountryById(countryId: Int): CountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)
}