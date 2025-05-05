package com.micahnyabuto.coinsphere.ui.screens.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.repository.CoinSphereRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel@Inject constructor(
    private val coinSphereRepository: CoinSphereRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow<UiState<List<Coin>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Coin>>> = _uiState.asStateFlow()

    fun fetchCoins(){
        viewModelScope.launch {

            _uiState.value = UiState.Loading
            try {
            val coins =coinSphereRepository.getCoins()
            _uiState.value = UiState.Success(coins)
            } catch (
               e: Exception
            ){
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unexpected error")

            }

        }
    }


}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
