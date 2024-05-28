package com.sofascore.minisofa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sofascore.minisofa.data.event_models.Sport
import com.sofascore.minisofa.data.local.entity.TournamentEntity

@Dao
interface TournamentDao {
    @Query("SELECT * FROM tournaments WHERE id = :tournamentId")
    suspend fun getTournamentById(tournamentId: Int): TournamentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tournaments: List<TournamentEntity>)

    @Query("SELECT * FROM tournaments WHERE sport = :sport")
    suspend fun getTournamentsBySport(sport: String) : List<TournamentEntity>
}
