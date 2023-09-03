package com.example.pokemon.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pokemon.model.ResultsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(
    entities = [
        ResultsModel::class,
    ], version = 1
)

@TypeConverters(ResultsModelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ListPokemonDao(): ListPokemonDao
}

abstract class Converters<T> {

    @TypeConverter
    fun mapListToString(value: List<T>): String {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<T> {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(value, type)
    }
}

class ResultsModelConverter : Converters<ResultsModel>()

