package com.sofascore.minisofa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sofascore.minisofa.R
import com.sofascore.minisofa.data.event_models.TeamStanding
import com.sofascore.minisofa.databinding.ItemStandingsAmericanFootballBinding
import com.sofascore.minisofa.databinding.ItemStandingsBasketballBinding
import com.sofascore.minisofa.databinding.ItemStandingsFootballBinding
import com.sofascore.minisofa.utils.SportType

class StandingsAdapter(private val sportType: SportType) : ListAdapter<TeamStanding, RecyclerView.ViewHolder>(StandingsDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == VIEW_TYPE_HEADER) {
//            val binding = ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            HeaderViewHolder(binding)
//        } else {
//            val binding = ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            StandingsViewHolder(binding)
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = when (sportType) {
                SportType.FOOTBALL -> ItemStandingsFootballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SportType.BASKETBALL -> ItemStandingsBasketballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SportType.AMERICAN_FOOTBALL -> ItemStandingsAmericanFootballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            HeaderViewHolder(binding)
        } else {
            val binding = when (sportType) {
                SportType.FOOTBALL -> ItemStandingsFootballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SportType.BASKETBALL -> ItemStandingsBasketballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SportType.AMERICAN_FOOTBALL -> ItemStandingsAmericanFootballBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
            StandingsViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(sportType)
        } else if (holder is StandingsViewHolder) {
            holder.bind(getItem(position - 1), sportType)
        }
    }

    class HeaderViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sportType: SportType) {
        }
    }

    class StandingsViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(standing: TeamStanding, sportType: SportType) {
            val textColor = binding.root.context.getColor(R.color.on_surface_on_surface_lv_1)
            when (binding) {
                is ItemStandingsFootballBinding -> {
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

                    binding.goalsLabel.text = standing.goals ?: "-"
                    binding.goalsLabel.setTextColor(textColor)

                    binding.pointsLabel.text = standing.points.toString()
                    binding.pointsLabel.setTextColor(textColor)
                }
                is ItemStandingsBasketballBinding -> {
                    binding.positionStandingsLabel.text = standing.position.toString()
                    binding.positionStandingsLabel.background = binding.root.context.getDrawable(R.drawable.circle_background)
                    binding.positionStandingsLabel.setTextColor(textColor)

                    binding.teamNameLabel.text = standing.teamName
                    binding.teamNameLabel.setTextColor(textColor)

                    binding.playedLabel.text = standing.played.toString()
                    binding.playedLabel.setTextColor(textColor)

                    binding.wonLabel.text = standing.won.toString()
                    binding.wonLabel.setTextColor(textColor)

                    binding.lostLabel.text = standing.lost.toString()
                    binding.lostLabel.setTextColor(textColor)

                    binding.diffLabel.text = standing.diff?.toString() ?: "-"
                    binding.diffLabel.setTextColor(textColor)

                    binding.streakLabel.text = standing.streak?.toString() ?: "-"
                    binding.streakLabel.setTextColor(textColor)

                    binding.gbLabel.text = standing.gb?.let { if (it != 0.0) it.toString() else "-" }
                    binding.gbLabel.setTextColor(textColor)

                    binding.percentageLabel.text = "%.3f".format(standing.percentage ?: 0.0).replace(",",".")
                    binding.percentageLabel.setTextColor(textColor)
                }
                is ItemStandingsAmericanFootballBinding -> {
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

                    binding.percentageLabel.text = "%.3f".format(standing.percentage ?: 0.0).replace(",",".")
                    binding.percentageLabel.setTextColor(textColor)
                }
            }
        }
        }
    }

    class StandingsDiffCallback : DiffUtil.ItemCallback<TeamStanding>() {
        override fun areItemsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            val areSame = oldItem.position == newItem.position
            return areSame
        }

        override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            val areContentsSame = oldItem == newItem
            return areContentsSame
        }
    }

