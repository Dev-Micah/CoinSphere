package com.micahnyabuto.coinsphere.ui.screens.market

import android.util.Log
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MarketViewModel@Inject constructor(
    private val coinSphereRepository: CoinSphereRepository
) : ViewModel(){

    var marketUiState = MutableStateFlow< MarketUiState>(MarketUiState.Loading)
    private set


    private val _allCoins = MutableStateFlow<List<Coin>>(emptyList())
    val allCoins = _allCoins.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()



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
            _isLoading.value =true
            _error.value = null

            marketUiState.update {
                try {
                    val listResult = coinSphereRepository.getCoins()

                    _allCoins.value =listResult

                    Log.e("MarketViewModel" ,"$listResult")
                    MarketUiState.Success(coins = listResult)
                } catch (e: IOException){
                    MarketUiState.Error
                }

            }

        }
    }


    fun onSearchQueryChange(query: String){
        _searchQuery.value =query

    }


}
sealed interface MarketUiState{
    object Loading : MarketUiState
    data class Success(val coins: List<Coin>): MarketUiState
    data object Error: MarketUiState
}
