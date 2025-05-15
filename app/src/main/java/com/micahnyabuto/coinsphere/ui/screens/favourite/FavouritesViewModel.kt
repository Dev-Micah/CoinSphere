package com.micahnyabuto.coinsphere.ui.screens.favourite

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.coinsphere.data.remote.Coin
import com.micahnyabuto.coinsphere.data.repository.CoinSphereRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavouritesViewModel@Inject constructor(

) :ViewModel(){

    private  val _favouriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favouriteIds: StateFlow<Set<String>> =_favouriteIds.asStateFlow()

    private val _allCoins = MutableStateFlow<List<Coin>>(emptyList())

    val favouriteCoins: StateFlow<List<Coin>> = combine (_allCoins, _favouriteIds){ coins , ids ->
        coins.filter { it.id in ids }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()

    )
    fun setAllCoins(coins: List<Coin>){
        _allCoins.value =coins
    }

    fun toggleFavourite(coinId: String){
        _favouriteIds.update { ids->
            if (ids.contains(coinId)) ids- coinId else ids + coinId
        }

    }
    fun isFavourite(coinId: String): Boolean{
        return _favouriteIds.value.contains(coinId)
    }


}