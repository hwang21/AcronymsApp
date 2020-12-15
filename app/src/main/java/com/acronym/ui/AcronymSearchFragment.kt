package com.acronym.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.acronym.R
import com.acronym.adapter.MainAdapter
import com.acronym.api.RetrofitInstance
import com.acronym.model.LongForm
import com.acronym.utils.SearchState
import com.acronym.utils.getQueryTextChangeStateFlow
import com.acronym.viewmodel.MainViewModel
import com.acronym.viewmodel.VariationsViewModel
import com.acronym.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AcronymSearchFragment : Fragment() {

    companion object {
        fun newInstance() = AcronymSearchFragment()
    }

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(RetrofitInstance.apiService)
    }

    private val variationViewModel by viewModels<VariationsViewModel>()

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setUpSearchStateFlow()
    }

    @ExperimentalCoroutinesApi
    private fun setUpSearchStateFlow() {
        val toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT)
            .also { toast ->
            toast.setGravity(
                Gravity.TOP or Gravity.CENTER,
                0,
                400)
            }
        lifecycleScope.launchWhenStarted {
            search_view.getQueryTextChangeStateFlow()
            .debounce(400)
            .filter { it.length > 1 }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                mainViewModel.searchState.also{
                    mainViewModel.searchAcronym(query)
                }
            }
            .collect {
                when(it) {
                    is SearchState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    is SearchState.Success -> {
                        recyclerView.visibility = View.VISIBLE
                        retrieveList(it.data[0].lfs)
                        progressBar.visibility = View.GONE
                    }
                    is SearchState.Empty -> {
                        toast.also { toast ->
                            toast.setText("No Result Found")
                        }
                        .show()
                        recyclerView.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }
                    is SearchState.Error -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        it.message?.let { message ->
                            toast.also { toast ->
                                toast.setText(message)
                            }
                            .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUI() {

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf()) { data ->
            variationViewModel.data = data.vars
            activity?.apply {
               supportFragmentManager.beginTransaction()
                   .replace(R.id.fragment, VariationsFragment.newInstance())
                   .addToBackStack(null)
                   .commit()
           }
        }
        recyclerView.adapter = adapter
    }

    private fun retrieveList(data: List<LongForm>?) {
        data?.let{
            adapter.apply {
                addData(it)
                notifyDataSetChanged()
            }
        }
    }
}