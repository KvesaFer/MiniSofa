package com.sofascore.minisofa.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sofascore.minisofa.R
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
            val textColor = binding.root.context.getColor(R.color.on_surface_on_surface_lv_1)

            binding.positionStandingsLabel.text = standing.position.toString()
            binding.positionStandingsLabel.background = binding.root.context.getDrawable(R.drawable.circle_background)
            binding.positionStandingsLabel.setTextColor(textColor)

            binding.teamNameLabel.text = standing.teamName
            binding.teamNameLabel.setTextColor(textColor)

            binding.playedLabel.text = standing.played.toString()
            binding.playedLabel.setTextColor(textColor)

            binding.wonLabel.text = standing.won.toString()
            binding.wonLabel.setTextColor(textColor)

            binding.drawLabel.text = standing.draw.toString()
            binding.drawLabel.setTextColor(textColor)

            binding.lostLabel.text = standing.lost.toString()
            binding.lostLabel.setTextColor(textColor)

            binding.goalsLabel.text = standing.goals
            binding.goalsLabel.setTextColor(textColor)

            binding.pointsLabel.text = standing.points.toString()
            binding.pointsLabel.setTextColor(textColor)
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
