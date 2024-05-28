package com.sofascore.minisofa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sofascore.minisofa.data.event_models.toEventInfo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "events")
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
    val tournamentName: String,
    val homeTeamCountry: String,
    val awayTeamCountry: String,
    val homeTeamLogo: String,
    val awayTeamLogo: String,
    val startTime: String,
    var tournamentLogo: String,
    var tournamentCountry: String
)