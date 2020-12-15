package com.acronym.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acronym.R
import com.acronym.model.Variation
import kotlinx.android.synthetic.main.row_layout.view.*

class VariationAdapter(private val data: ArrayList<Variation>):
    RecyclerView.Adapter<VariationAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Variation) {
            itemView.apply {
                textView.text = data.lf
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addData(data: List<Variation>) {
        this.data.apply {
            clear()
            addAll(data)
        }
    }

    fun clearData() {
        this.apply{
            data.clear()
            notifyDataSetChanged()
        }
    }
}