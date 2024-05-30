package com.sofascore.minisofa.utils

enum class SportType(val apiName: String, val displayName: String) {
    FOOTBALL("football", "Football"),
    BASKETBALL("basketball", "Basketball"),
    AMERICAN_FOOTBALL("american-football", "American Football");

    companion object {
        fun fromApiName(apiName: String): SportType? {
            return values().find { it.apiName == apiName }
        }

        fun fromDisplayName(displayName: String): SportType? {
            return values().find { it.displayName == displayName }
        }
    }
}
