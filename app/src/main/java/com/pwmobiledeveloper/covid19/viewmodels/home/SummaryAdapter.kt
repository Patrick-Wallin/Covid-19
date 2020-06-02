package com.pwmobiledeveloper.covid19.viewmodels.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pwmobiledeveloper.covid19.databinding.ListCasesBinding
import com.pwmobiledeveloper.covid19.databinding.ListCasesBindingImpl
import com.pwmobiledeveloper.covid19.model.database.TableCountriesSummary
import timber.log.Timber

class SummaryAdapter(val recyclerView: RecyclerView): ListAdapter<TableCountriesSummary, SummaryAdapter.ViewHolder>(SummaryDiffCallback()) {
    override fun onBindViewHolder(holder: SummaryAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onCurrentListChanged(
        previousList: MutableList<TableCountriesSummary>,
        currentList: MutableList<TableCountriesSummary>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        Timber.d("oh you change")
        recyclerView.scrollToPosition(0)

    }
    class ViewHolder private constructor(val binding: ListCasesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TableCountriesSummary) {
            binding.countriessummary = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListCasesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SummaryDiffCallback : DiffUtil.ItemCallback<TableCountriesSummary>() {
    override fun areItemsTheSame(
        oldItem: TableCountriesSummary,
        newItem: TableCountriesSummary
    ): Boolean {
        return oldItem.slug == newItem.slug
    }

    override fun areContentsTheSame(
        oldItem: TableCountriesSummary,
        newItem: TableCountriesSummary
    ): Boolean {
        return oldItem == newItem
    }

}