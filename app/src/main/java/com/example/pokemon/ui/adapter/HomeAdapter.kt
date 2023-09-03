package com.example.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.ItemHomeBinding
import com.example.pokemon.model.ResultsModel
import android.util.Log
import com.google.gson.Gson

class HomeAdapter(private val listenerClick: (data: ResultsModel) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var result: List<ResultsModel> = ArrayList<ResultsModel>()

    inner class HomeViewHolder(binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textNama = binding.txtName
        val cardItem = binding.cardItem
    }

    fun setItemList(list: List<ResultsModel>?) {
        Log.d("itemcek = ", Gson().toJson(list))
        if (list != null) {
            this.result = list
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                textNama.text = name
                holder.cardItem.setOnClickListener {
                    listenerClick.invoke(this)
                }
            }

        }


    }

}