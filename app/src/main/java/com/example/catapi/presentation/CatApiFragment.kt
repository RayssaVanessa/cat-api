package com.example.catapi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapi.databinding.FragmentCatApiBinding
import com.example.catapi.presentation.adapter.CatAdapter
import com.example.catapi.presentation.viewmodel.CatViewModel
import com.example.catapi.presentation.viewmodel.action.CatAction
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CatApiFragment : Fragment() {

    private val viewModel: CatViewModel by inject()
    private val binding: FragmentCatApiBinding by lazy { FragmentCatApiBinding.inflate(layoutInflater) }
    private val adapter: CatAdapter by lazy { CatAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.handleAction(CatAction.LoadData)
        viewModel.handleAction(CatAction.onClickedItem)
    }

    private fun setupRecyclerView() {
        binding.rvlistCats.layoutManager = LinearLayoutManager(context)
        binding.rvlistCats.adapter = adapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner) { state  ->
                state.isError?.let {
                    //handle error

                }
                state.isLoading?.let {
                    //handle loading
                }
                state.catList?.let {
                    adapter.submitList(it)
                }
                state.messageState?.let {
                    //handle message
                    Toast.makeText(context, "Carregado", Toast.LENGTH_SHORT).show()
                }
//                adapter.submitList()
           }
        }
    }
}