package com.micahnyabuto.coinsphere.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.coinsphere.domain.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FavouritesViewModel() :ViewModel(){

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