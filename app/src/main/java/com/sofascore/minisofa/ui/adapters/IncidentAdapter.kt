package com.sofascore.minisofa.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sofascore.minisofa.R
import com.sofascore.minisofa.data.event_models.Incident
import com.sofascore.minisofa.databinding.BasketballAwayScoreBinding
import com.sofascore.minisofa.databinding.BasketballHomeScoreBinding
import com.sofascore.minisofa.databinding.FootballAwayGoalBinding
import com.sofascore.minisofa.databinding.FootballHomeGoalBinding
import com.sofascore.minisofa.databinding.FootballIncidentAwayBinding
import com.sofascore.minisofa.databinding.FootballIncidentHomeBinding
import com.sofascore.minisofa.databinding.HtFtHeaderBinding
import com.sofascore.minisofa.utils.SportType

class IncidentAdapter(
    private val onItemClick: (Incident) -> Unit
): ListAdapter<Incident, RecyclerView.ViewHolder>(IncidentDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_GOAL_HOME = 1
        private const val VIEW_TYPE_GOAL_AWAY = 2
        private const val VIEW_TYPE_CARD_HOME = 3
        private const val VIEW_TYPE_CARD_AWAY = 4
        private const val VIEW_TYPE_PERIOD = 5
        private const val VIEW_TYPE_BASKETBALL_HOME = 6
        private const val VIEW_TYPE_BASKETBALL_AWAY = 7
    }

    override fun getItemViewType(position: Int): Int {
        val incident = getItem(position)
        return when (incident.sport) {
            SportType.FOOTBALL.displayName -> when (incident.type) {
                "goal" -> if (incident.scoringTeam == "home") VIEW_TYPE_GOAL_HOME else VIEW_TYPE_GOAL_AWAY
                "card" -> if (incident.teamSide == "home") VIEW_TYPE_CARD_HOME else VIEW_TYPE_CARD_AWAY
                "period" -> VIEW_TYPE_PERIOD
                else -> if (incident.teamSide == "home") VIEW_TYPE_CARD_HOME else VIEW_TYPE_CARD_AWAY
            }
            SportType.BASKETBALL.displayName -> when(incident.type) {
                "goal" -> if (incident.scoringTeam == "home") VIEW_TYPE_BASKETBALL_HOME else VIEW_TYPE_BASKETBALL_AWAY
                "period" -> VIEW_TYPE_PERIOD
                else -> throw IllegalArgumentException("Invalid incident type for basketball")
            }
            SportType.AMERICAN_FOOTBALL.displayName -> when (incident.type) {
                "goal" -> if (incident.scoringTeam == "home") VIEW_TYPE_GOAL_HOME else VIEW_TYPE_GOAL_AWAY
                "card" -> if (incident.teamSide == "home") VIEW_TYPE_CARD_HOME else VIEW_TYPE_CARD_AWAY
                "period" -> VIEW_TYPE_PERIOD
                else -> if (incident.teamSide == "home") VIEW_TYPE_CARD_HOME else VIEW_TYPE_CARD_AWAY
            }
            else -> throw IllegalArgumentException("Invalid sport type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_GOAL_HOME -> FootballHomeGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_GOAL_AWAY -> FootballAwayGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_CARD_HOME -> FootballIncidentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_CARD_AWAY -> FootballIncidentAwayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_PERIOD -> HtFtHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_BASKETBALL_HOME -> BasketballHomeScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_BASKETBALL_AWAY -> BasketballAwayScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> throw IllegalArgumentException("Invalid view type")
        }
        return IncidentViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val incident = getItem(position)
        (holder as IncidentViewHolder).bind(incident)
    }

    inner class IncidentViewHolder(
        private val binding: ViewBinding,
        private val onItemClick: (Incident) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(incident: Incident) {
            binding.root.setOnClickListener {
                onItemClick(incident)
            }
            when (binding) {
                is FootballHomeGoalBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.playerName.text = incident.player?.name
                    binding.homeTeamScore.text = incident.homeScore.toString()
                    binding.awayTeamScore.text = incident.awayScore.toString()
                    binding.incidentLogo.setImageResource(
                        when(incident.goalType) {
                            "regular" -> R.drawable.ic_football_goal
                            "owngoal" -> R.drawable.incidents_football_ic_autogoal
                            "penalty" -> R.drawable.incidents_football_ic_penalty_score

                            "touchdown" -> R.drawable.ic_touchdown
                            "fieldgoal" -> R.drawable.ic_field_goal
                            "extrapoint" -> R.drawable.ic_extra_point
                            "safety" -> R.drawable.ic_rogue
                            "twopointconversion" -> R.drawable.ic_two_point_conversion
                            else -> {R.drawable.ic_football_goal}
                        }
                    )
                }
                is FootballAwayGoalBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.playerName.text = incident.player?.name
                    binding.homeTeamScore.text = incident.homeScore.toString()
                    binding.awayTeamScore.text = incident.awayScore.toString()
                    binding.incidentLogo.setImageResource(
                        when(incident.goalType) {
                            "regular" -> R.drawable.ic_football_goal
                            "owngoal" -> R.drawable.incidents_football_ic_autogoal
                            "penalty" -> R.drawable.incidents_football_ic_penalty_score
                            "touchdown" -> R.drawable.ic_touchdown
                            "fieldgoal" -> R.drawable.ic_field_goal
                            "extrapoint" -> R.drawable.ic_extra_point
                            "safety" -> R.drawable.ic_rogue
                            "twopointconversion" -> R.drawable.ic_two_point_conversion
                            else -> {R.drawable.ic_football_goal}
                        }
                    )
                }


                is FootballIncidentHomeBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.playerName.text = incident.player?.name
                    binding.incidentCause.text = incident.color
                    binding.incidentLogo.setImageResource(
                        when (incident.color) {
                            "yellow" -> R.drawable.incidents_football_ic_card_yellow
                            "red" -> R.drawable.incidents_football_ic_card_red
                            else -> { R.drawable.incidents_football_ic_card_red }
                        }
                    )
                }
                is FootballIncidentAwayBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.playerName.text = incident.player?.name
                    val color = when (incident.color) {
                        "yellow" -> "Yellow"
                        "red" -> "Red"
                        "yellowred" -> "Second Yellow"
                        else -> ""
                    }
                    binding.incidentCause.text = color // Nigdje nije naveden api za razlog (npr. faul ili svađanje)
                    binding.incidentLogo.setImageResource(
                        when (incident.color) {
                            "yellow" -> R.drawable.incidents_football_ic_card_yellow
                            "red" -> R.drawable.incidents_football_ic_card_red
                            else -> { R.drawable.incidents_football_ic_card_red }
                        }
                    )
                }
                is HtFtHeaderBinding -> {
                    Log.d("IncidentAdapter", "HT/FT incident values: $incident")
                    binding.twHtFt.text = incident.text
                }
                is BasketballHomeScoreBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.homeTeamScore.text = incident.homeScore.toString()
                    binding.awayTeamScore.text = incident.awayScore.toString()
                    binding.incidentLogo.setImageResource(
                        when (incident.goalType) {
                            "onepoint" -> R.drawable.ic_num_basketball_incident_1
                            "twopoint" -> R.drawable.ic_num_basketball_incident_2
                            "threepoint" -> R.drawable.ic_num_basketball_incident_3
                            else -> R.drawable.ic_num_basketball_incident_2
                        }
                    )
                }
                is BasketballAwayScoreBinding -> {
                    binding.minuteOfIncident.text = "${incident.time}'"
                    binding.homeTeamScore.text = incident.homeScore.toString()
                    binding.awayTeamScore.text = incident.awayScore.toString()
                    binding.incidentLogo.setImageResource(
                        when (incident.goalType) {
                            "onepoint" -> R.drawable.ic_num_basketball_incident_1
                            "twopoint" -> R.drawable.ic_num_basketball_incident_2
                            "threepoint" -> R.drawable.ic_num_basketball_incident_3
                            else -> R.drawable.ic_num_basketball_incident_2
                        }
                    )
                }
            }
        }
    }

    class IncidentDiffCallback : DiffUtil.ItemCallback<Incident>() {
        override fun areItemsTheSame(oldItem: Incident, newItem: Incident): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Incident, newItem: Incident): Boolean {
            return oldItem == newItem
        }
    }
}
