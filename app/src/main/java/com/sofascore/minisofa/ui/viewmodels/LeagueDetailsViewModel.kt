package com.sofascore.minisofa.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofascore.minisofa.data.local.entity.CountryEntity
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.data.repository.CountryRepository
import com.sofascore.minisofa.data.repository.TournamentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LeagueDetailsViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _tournamentDetails = MutableLiveData<TournamentEntity?>()
    private val _country = MutableLiveData<CountryEntity?>()
    val tournamentDetails: MutableLiveData<TournamentEntity?> get() = _tournamentDetails
    val country: MutableLiveData<CountryEntity?> get() = _country

    fun fetchTournamentDetails(id: Int) {
        viewModelScope.launch {
            try {
                val details = tournamentRepository.getTournamentDetails(id)
                val country = details?.let { countryRepository.getCountryById(it.countryId) }
                _tournamentDetails.value = details
                _country.value = country
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}