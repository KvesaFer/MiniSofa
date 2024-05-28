package com.sofascore.minisofa.di

import android.content.Context
import androidx.room.Room
import com.sofascore.minisofa.data.event_models.Country
import com.sofascore.minisofa.data.local.SofascoreDatabase
import com.sofascore.minisofa.data.local.dao.CountryDao
import com.sofascore.minisofa.data.local.dao.EventDao
import com.sofascore.minisofa.data.local.dao.TournamentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideEventDao(sofascoreDatabase: SofascoreDatabase): EventDao = sofascoreDatabase.eventDao()

    @Provides
    fun provideCountryDao(sofascoreDatabase: SofascoreDatabase): CountryDao = sofascoreDatabase.countryDao()

    @Provides
    fun provideTournamentDao(sofascoreDatabase: SofascoreDatabase): TournamentDao = sofascoreDatabase.tournamentDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SofascoreDatabase =
        Room.databaseBuilder(
            context,
            SofascoreDatabase::class.java,
            "sofascore_database"
            ).fallbackToDestructiveMigration()
            .build()
}