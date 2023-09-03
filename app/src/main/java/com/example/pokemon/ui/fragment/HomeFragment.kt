package com.example.pokemon.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentHomeBinding
import com.example.pokemon.model.ResultsModel
import com.example.pokemon.ui.adapter.HomeAdapter
import com.example.pokemon.utility.CustomDialog
import com.example.pokemon.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var newDialog: CustomDialog

    lateinit var binding: FragmentHomeBinding
    val homeViewModel: HomeViewModel by viewModels()
    lateinit var adapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbarHome)

        (activity as AppCompatActivity?)?.title = "Home"

        binding.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                v.hideKeyboard()
                viewLifecycleOwner.lifecycleScope.launch {
                    filterSearch(v.text.toString())
                }
                return@setOnEditorActionListener true
            }
            false
        }

        this.adapter = HomeAdapter(
            listenerClick = {
                val bundle = bundleOf(
                    "id" to it.url?.substring(it.url?.length?.minus(2) ?: 0)?.replace("/", "")
                )
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        )

        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.getListPokemon()
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                adapter.setItemList(it)
            }
        }

        homeViewModel.isShowDialog.observe(requireActivity()) {
            if (it) {
                newDialog.showDialog()
            } else {
                newDialog.dismisDialog()
            }
        }


        binding.rvHome.run {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun filterSearch(text: String) {
        val dataSearch: ArrayList<ResultsModel> = ArrayList()
        val searchTextQuery = "%$text%"

        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.searchData(searchTextQuery)
                .observe(viewLifecycleOwner) { data ->
                    adapter.setItemList(data)
                }
        }

    }
}