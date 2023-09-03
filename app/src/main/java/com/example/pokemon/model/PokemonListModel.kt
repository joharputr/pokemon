package com.example.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class PokemonListModel(

    @SerializedName("count") var count: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: List<ResultsModel> = arrayListOf()

)

@Entity(tableName = "ResultsModel")
data class ResultsModel(
    @SerializedName("id") @PrimaryKey var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)