package com.sofascore.minisofa.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["homeTeamCountryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["awayTeamCountryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("homeTeamCountryId"), Index("awayTeamCountryId")]
)
data class EventInfo(
    @PrimaryKey val id: Int,
    val date: String,
    val homeName: String,
    val awayName: String,
    val homeScore: Int,
    val awayScore: Int,
    val status: String,
    val sport: String,
    val tournamentId: Int,
    val homeTeamCountryId: Int,
    val awayTeamCountryId: Int,
    val homeTeamLogo: String,
    val awayTeamLogo: String,
    val startTime: String,
    val round: Int
)
