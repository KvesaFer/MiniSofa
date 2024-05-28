package com.sofascore.minisofa.ui

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
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
import com.sofascore.minisofa.databinding.ItemTournamentHeaderBinding
import com.sofascore.minisofa.utils.bindImage

class EventAdapter : ListAdapter<Any, RecyclerView.ViewHolder>(EventComparator) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_EVENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TournamentEvents -> VIEW_TYPE_HEADER
            is EventInfo -> VIEW_TYPE_EVENT
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemTournamentHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TournamentViewHolder(binding)
            }
            VIEW_TYPE_EVENT -> {
                val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EventViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TournamentViewHolder -> holder.bind(getItem(position) as TournamentEvents)
            is EventViewHolder -> holder.bind(getItem(position) as EventInfo)
        }
    }

    class TournamentViewHolder(private val binding: ItemTournamentHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentEvents: TournamentEvents) {
            binding.tournamentCountry.text = tournamentEvents.tournamentCountry
            binding.tournamentName.text = tournamentEvents.tournamentName
            Glide.with(binding.tournamentLogo.context)
                .load(tournamentEvents.tournamentLogo)
                .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_error))
                .into(binding.tournamentLogo)

            binding.hline.visibility = if (position == 0) View.GONE else View.VISIBLE
        }
    }

    class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventInfo) {
            val context = binding.root.context
            binding.matchTime.text = event.startTime
            binding.homeTeamName.text = event.homeName
            binding.homeTeamScore.text = event.homeScore.toString()
            binding.awayTeamName.text = event.awayName
            binding.awayTeamScore.text = event.awayScore.toString()
            bindImage(binding.homeTeamLogo, event.homeTeamLogo)
            bindImage(binding.awayTeamLogo, event.awayTeamLogo)
            binding.homeTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
            binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
            binding.awayTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
            binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
            setScoreColors(event)
            setMatchStatus(event)
            binding.executePendingBindings()
        }

        private fun setMatchStatus(event: EventInfo) {
            val context = binding.root.context
            when {
                event.status == "finished" -> {
                    binding.matchStatus.text = "FT"
                }
                event.status == "notstarted" -> {
                    binding.matchStatus.text = "-"
                }
                else -> {
                    binding.matchStatus.text = "${event.status}'"
                    binding.matchStatus.setTextColor(ContextCompat.getColor(context, R.color.specific_live))
                }
            }
        }

        private fun setScoreColors(event: EventInfo) {
            val context = binding.root.context
            val homeScore = event.homeScore
            val awayScore = event.awayScore

            if (event.status == "notstarted") {
                binding.homeTeamScore.visibility = View.GONE
                binding.awayTeamScore.visibility = View.GONE
            }
            if (event.status != "notstarted") {
                binding.homeTeamScore.visibility = View.VISIBLE
                binding.awayTeamScore.visibility = View.VISIBLE
            }
            if (event.status != "finished" && event.status != "notstarted") {
                binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.specific_live))
                binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.specific_live))
            }

            if (event.status == "finished") {
                when {
                    homeScore > awayScore-> {
                        binding.homeTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                        binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                        binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.awayTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                    }
                    homeScore < awayScore -> {
                        binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.homeTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                        binding.awayTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_1))
                    }
                    else -> {
                        binding.homeTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.homeTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.awayTeamScore.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                        binding.awayTeamName.setTextColor(ContextCompat.getColor(context, R.color.on_surface_on_surface_lv_2))
                    }
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

    object EventComparator : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return (oldItem is TournamentEvents && newItem is TournamentEvents && oldItem.tournamentName == newItem.tournamentName)
                || (oldItem is EventInfo && newItem is EventInfo && oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }
    }
}