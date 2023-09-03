package com.example.pokemon.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon.base.BaseViewModel
import com.example.pokemon.base.handleResponseV2
import com.example.pokemon.database.ListPokemonDao
import com.example.pokemon.model.Abilities
import com.example.pokemon.model.DetailModel
import com.example.pokemon.model.ResultsModel
import com.example.pokemon.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dao: ListPokemonDao
) :
    BaseViewModel() {

    var detailData = MutableLiveData<DetailModel>()

    suspend fun getListPokemon() {
        viewModelScope.launch {
            changeShow(true)
            dao.removeAll()

            homeRepository.getListPokemon().handleResponseV2(onSuccess = {
                it.results.forEach { data ->
                    dao.insert(data)
                }
                changeShow(false)
            }, onFailed = {
                changeShow(false)
            })
        }
    }

    suspend fun getAllData(): LiveData<List<ResultsModel>> {
        return dao.getAll()
    }

    suspend fun searchData(query: String): LiveData<List<ResultsModel>> {
        return dao.searchTask(query)
    }


    suspend fun getDetailPokemon(id: Int) {
        viewModelScope.launch {
            changeShow(true)
            dao.removeAll()

            homeRepository.getDetailPokemon(id).handleResponseV2(onSuccess = {
                detailData.postValue(it)
                changeShow(false)
            }, onFailed = {
                changeShow(false)
            })
        }
    }
}