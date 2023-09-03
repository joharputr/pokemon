package com.example.pokemon.model

import com.google.gson.annotations.SerializedName


data class DetailModel(
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("abilities") var abilities: ArrayList<Abilities> = arrayListOf(),

    )


data class Abilities(

    @SerializedName("ability") var ability: Ability? = Ability(),
    @SerializedName("is_hidden") var isHidden: Boolean? = null,
    @SerializedName("slot") var slot: Int? = null

)

data class Ability(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

)

data class Sprites(
    @SerializedName("front_default") var frontDefault: String? = null,

    )