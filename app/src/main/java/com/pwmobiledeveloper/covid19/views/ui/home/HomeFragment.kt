package com.pwmobiledeveloper.covid19.views.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwmobiledeveloper.covid19.R
import com.pwmobiledeveloper.covid19.databinding.FragmentHomeBinding
import com.pwmobiledeveloper.covid19.viewmodels.home.SummaryAdapter
import com.pwmobiledeveloper.covid19.viewmodels.home.SummaryViewModel
import com.pwmobiledeveloper.covid19.viewmodels.home.SummaryViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    val activityScope = CoroutineScope(Dispatchers.Main)
    lateinit var viewModel : SummaryViewModel
    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = SummaryViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SummaryViewModel::class.java)
        viewModel.summaryGlobal.observe(this, Observer {
            binding.globalSummary = it
        })

        binding.summaryViewModel = viewModel

        val adapter = SummaryAdapter(binding.casesRecyclerview)

        //binding.casesRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.casesRecyclerview.adapter = adapter
        viewModel.summaryCountries.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.submitList(it)
                Timber.d("adapter submit")
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_menu_option, menu)

        //val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.option_search)
        if(searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    searchView.setQuery("", false)
                    searchItem.collapseActionView()
                    Timber.d("looking for $query")

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }



        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_search -> {
                return true
            }
            R.id.option_sort_country_a_to_z -> {
                Timber.d("sort country a to z")
                viewModel.sortCountries("alphabetical_a_to_z")
               // binding.casesRecyclerview.layoutManager?.scrollToPosition(0)
                //binding.casesRecyclerview.adapter?.notifyDataSetChanged()
                //adapter.currentList.clear()
                return true
            }
            R.id.option_sort_country_z_to_a -> {
                viewModel.sortCountries("alphabetical_z_to_a")

                return true
            }
            R.id.option_sort_total_confirmed_high_to_low -> {
                viewModel.sortCountries("total_confirmed_high_to_low")

                return true
            }
            R.id.option_sort_total_confirmed_low_to_high -> {
                viewModel.sortCountries("total_confirmed_low_to_high")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

