package com.example.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.example.pokemon.databinding.ItemAbilitiesBinding
import com.example.pokemon.model.Abilities
import com.google.gson.Gson

class DetailAdapter() :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    private var result: List<Abilities> = ArrayList<Abilities>()

    inner class DetailViewHolder(binding: ItemAbilitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textNama = binding.txtName
    }

    fun setItemList(list: List<Abilities>?) {
        Log.d("itemcek = ", Gson().toJson(list))
        if (list != null) {
            this.result = list
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding =
            ItemAbilitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                textNama.text = this.ability?.name
            }

        }


    }

}