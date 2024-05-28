package com.sofascore.minisofa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sofascore.minisofa.R
import com.sofascore.minisofa.data.local.entity.EventInfo
import com.sofascore.minisofa.databinding.ItemEventBinding

class EventAdapter : ListAdapter<EventInfo, EventAdapter.EventViewHolder>(EventComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        if (event != null) {
            holder.bind(event)
        }
    }

    class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventInfo) {
            binding.matchTime.text = event.startTime
            binding.matchStatus.text = if (event.status == "finished") "FT" else "-"
            binding.homeTeamName.text = event.homeName
            binding.homeTeamScore.text = event.homeScore.toString()
            binding.awayTeamName.text = event.awayName
            binding.awayTeamScore.text = event.awayScore.toString()
            bindImage(binding.homeTeamLogo, event.homeTeamLogo)
            bindImage(binding.awayTeamLogo, event.awayTeamLogo)
            setScoreColors(event)
            binding.executePendingBindings()
        }

        private fun setScoreColors(event: EventInfo) {
            val context = binding.root.context
            val homeScore = event.homeScore
            val awayScore = event.awayScore

            when {
                homeScore > awayScore -> {
                    binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                    binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                }
                homeScore < awayScore -> {
                    binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                    binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                }
                else -> {
                    binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                    binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                }
            }
        }

        private fun bindImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_error)
                    )
                    .into(view)
            }
        }
    }

    object EventComparator : DiffUtil.ItemCallback<EventInfo>() {
        override fun areItemsTheSame(oldItem: EventInfo, newItem: EventInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventInfo, newItem: EventInfo): Boolean {
            return oldItem == newItem
        }
    }
}
