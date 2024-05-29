package com.sofascore.minisofa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.databinding.ItemStandingsBinding

class StandingsAdapter : ListAdapter<TeamStanding, StandingsAdapter.StandingsViewHolder>(StandingsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        val binding = ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StandingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StandingsViewHolder(private val binding: ItemStandingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: TeamStanding) {
            binding.positionStandingsLabel.text = standing.position.toString()
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
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            return oldItem == newItem
        }
    }
}