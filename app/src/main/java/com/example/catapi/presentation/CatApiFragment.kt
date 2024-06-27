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
    }

    private fun setupRecyclerView() {
        binding.rvlistCats.layoutManager = LinearLayoutManager(context)
        binding.rvlistCats.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isError != null) {
                Toast.makeText(context, " ${state.isError}", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

            if (state.isLoading == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (state.catList != null) {
                adapter.submitList(state.catList)
            }
        }
    }
}