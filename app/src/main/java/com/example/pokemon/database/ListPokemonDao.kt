package com.example.pokemon.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokemon.model.ResultsModel

@Dao
interface ListPokemonDao : BaseDao<ResultsModel> {
    @Query("SELECT * FROM ResultsModel")
    fun getAll(): LiveData<List<ResultsModel>>

    @Query("Delete FROM ResultsModel")
    fun removeAll()

    @Query("SELECT * FROM ResultsModel  WHERE name LIKE :query")
    fun searchTask(query: String): LiveData<List<ResultsModel>>
}

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T?)

    @Update
    fun update(vararg items: T?)

    @Delete
    fun delete(item: T?)


}