package com.sofascore.minisofa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val slug: String,
    val sport: String,
    val countryId: Int,
    val logoUrl: String
)