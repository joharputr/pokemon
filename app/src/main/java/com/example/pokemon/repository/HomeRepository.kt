package com.example.pokemon.repository

import com.example.pokemon.core.API
import com.example.pokemon.database.ListPokemonDao
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: API
) {
    suspend fun getListPokemon() = api.getData()
    suspend fun getDetailPokemon(id: Int) = api.getDataDetail(id)
}