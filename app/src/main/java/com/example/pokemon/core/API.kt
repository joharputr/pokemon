package com.example.pokemon.core

import com.example.pokemon.model.DetailModel
import com.example.pokemon.model.PokemonListModel
import com.example.pokemon.model.ResultsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("api/v2/pokemon/")
    suspend fun getData(
    ): Response<PokemonListModel>

    @GET("api/v2/pokemon/{id}")
    suspend fun getDataDetail(
        @Path("id") id: Int?
    ): Response<DetailModel>


}