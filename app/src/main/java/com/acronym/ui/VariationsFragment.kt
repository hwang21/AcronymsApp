package com.acronym.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.acronym.R
import com.acronym.adapter.VariationAdapter
import com.acronym.model.Variation
import com.acronym.viewmodel.VariationsViewModel
import kotlinx.android.synthetic.main.fragment_varations.*

class VariationsFragment: Fragment() {
    companion object {
        fun newInstance() = VariationsFragment()
    }

    private val variationViewModel by activityViewModels<VariationsViewModel>()

    private lateinit var adapter: VariationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_varations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        recyclerView_var.layoutManager = LinearLayoutManager(activity)
        adapter = VariationAdapter(variationViewModel.data!!)
        recyclerView_var.adapter = adapter
    }

    private fun retrieveList(data: List<Variation>?) {
        data?.let{
            adapter.apply {
                addData(it)
                notifyDataSetChanged()
            }
        }
    }
}