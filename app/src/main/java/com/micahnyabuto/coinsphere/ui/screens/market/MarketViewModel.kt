package com.micahnyabuto.coinsphere.ui.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.repository.CoinSphereRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel@Inject constructor(
    private val coinSphereRepository: CoinSphereRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState<List<Coin>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Coin>>> = _uiState.asStateFlow()

    private val _allCoins = MutableStateFlow<List<Coin>>(emptyList())

    val allCoins: StateFlow<List<Coin>> = _allCoins.asStateFlow()



    /*
    search query
     */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredCoins: StateFlow<List<Coin>> = combine(_allCoins, _searchQuery) { coins, query ->
        if (query.isBlank()) coins
        else coins.filter { coin ->
            coin.name.contains(query, ignoreCase = true) ||
                    coin.symbol.contains(query, ignoreCase = true)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    fun fetchCoins(){
        viewModelScope.launch {

            _uiState.value = UiState.Loading
            try {
            val coins =coinSphereRepository.getCoins()
                _allCoins.value =coins
            _uiState.value = UiState.Success(coins)
            } catch (
               e: Exception
            ){
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unexpected error")

            }

        }
    }

    fun onSearchQueryChange(query: String){
        _searchQuery.value =query

    }


}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
