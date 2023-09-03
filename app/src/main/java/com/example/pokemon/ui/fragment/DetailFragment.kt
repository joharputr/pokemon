package com.example.pokemon.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentDetailBinding
import com.example.pokemon.ui.adapter.DetailAdapter
import com.example.pokemon.ui.adapter.HomeAdapter
import com.example.pokemon.utility.CustomDialog
import com.example.pokemon.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var newDialog: CustomDialog
    lateinit var adapter: DetailAdapter
    lateinit var binding: FragmentDetailBinding

    val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbarDetail)

        (activity as AppCompatActivity?)?.title = "Detail"
        this.adapter = DetailAdapter()

        CoroutineScope(Dispatchers.Main).launch {

            arguments?.getString("id")?.let {
                homeViewModel.getDetailPokemon(it.toInt())

            }

            homeViewModel.detailData.observe(viewLifecycleOwner) {
                adapter.setItemList(it.abilities)
                binding.txtTitle.setText(it.name)
                Glide.with(requireContext())
                    .load(it.sprites?.frontDefault)
                    .into(binding.imgPoster)
            }
        }

        homeViewModel.isShowDialog.observe(requireActivity()) {
            if (it) {
                newDialog.showDialog()
            } else {
                newDialog.dismisDialog()
            }
        }


        binding.rvAbility.run {
            adapter = this@DetailFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

}