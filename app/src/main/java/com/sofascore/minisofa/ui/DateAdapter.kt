package com.sofascore.minisofa.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sofascore.minisofa.databinding.ItemDateBinding
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(private val dates: List<Date>, private val onDateSelected: (Date) -> Unit) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private val dayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
    private val dateFormat = SimpleDateFormat("dd.MM.", Locale.ENGLISH)
    private val todayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    init {
        selectedPosition = dates.indexOfFirst { date ->
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date) == todayFormat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, position == selectedPosition)
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onDateSelected(date)
        }
    }

    override fun getItemCount(): Int = dates.size

    inner class DateViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(date: Date, isSelected: Boolean) {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date)
            binding.tabDay.text = if (dateStr == todayFormat) "TODAY" else dayFormat.format(date).uppercase(Locale.ENGLISH)
            binding.tabDate.text = dateFormat.format(date)

            binding.selectionIndicator.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }
}
