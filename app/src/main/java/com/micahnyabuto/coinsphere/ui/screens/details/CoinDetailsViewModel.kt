package com.micahnyabuto.coinsphere.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.coinsphere.domain.model.Coin
import com.micahnyabuto.coinsphere.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class CoinDetailsViewModel(private val coinSphereRepository: CoinsRepository): ViewModel() {

    var coinUiState = MutableStateFlow<CoinUiState>(CoinUiState.Loading)
        private set

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun getDetails(){
        viewModelScope.launch {
            coinUiState.update {
                try {
                    _isLoading.value =true
                    _error.value= null
                    val listResult = coinSphereRepository.getCoins()

                    Log.e("CoinDetailsViewModel" ,"$listResult")

                    CoinUiState.Success(coin = listResult)
                }catch(e:IOException){
                CoinUiState.Error
            }
            }
        }
    }
}

sealed interface CoinUiState{
    data object Loading: CoinUiState
    data object Error: CoinUiState
    data class Success(val coin: List<Coin>): CoinUiState
}