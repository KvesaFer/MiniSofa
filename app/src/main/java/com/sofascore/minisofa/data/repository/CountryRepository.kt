package com.sofascore.minisofa.data.repository

import com.sofascore.minisofa.data.local.dao.CountryDao
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.remote.ApiService
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val apiService: ApiService,
    private val countryDao: CountryDao
) {
    suspend fun getCountryById(countryId: Int): CountryEntity? {
        return countryDao.getCountryById(countryId)
    }
}