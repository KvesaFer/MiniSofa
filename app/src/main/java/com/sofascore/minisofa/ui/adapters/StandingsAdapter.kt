package com.sofascore.minisofa.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.databinding.ItemStandingsBinding

class StandingsAdapter : ListAdapter<TeamStanding, StandingsAdapter.StandingsViewHolder>(StandingsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        Log.d("StandingsAdapter", "onCreateViewHolder called")
        val binding = ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StandingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        Log.d("StandingsAdapter", "onBindViewHolder called for position $position")
        holder.bind(getItem(position))
    }

    class StandingsViewHolder(private val binding: ItemStandingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: TeamStanding) {
            Log.d("StandingsAdapter", "Binding item at position: ${standing.position}")
            binding.positionStandingsLabel.text =standing.position.toString()
            binding.teamNameLabel.text = standing.teamName
            binding.playedLabel.text = standing.played.toString()
            binding.wonLabel.text = standing.won.toString()
            binding.drawLabel.text = standing.draw.toString()
            binding.lostLabel.text = standing.lost.toString()
            binding.goalsLabel.text = standing.goals
            binding.pointsLabel.text = standing.points.toString()
        }
    }

    class StandingsDiffCallback : DiffUtil.ItemCallback<TeamStanding>() {
        override fun areItemsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            val areSame = oldItem.position == newItem.position
            Log.d("StandingsDiffCallback", "areItemsTheSame: $areSame")
            return areSame
        }

        override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            val areContentsSame = oldItem == newItem
            Log.d("StandingsDiffCallback", "areContentsTheSame: $areContentsSame")
            return areContentsSame
        }
    }
}
