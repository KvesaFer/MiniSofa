package com.sofascore.minisofa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sofascore.minisofa.R
import com.sofascore.minisofa.data.local.entity.TournamentEntity
import com.sofascore.minisofa.databinding.ItemLeagueBinding

class LeagueAdapter(private val onLeagueClick: (TournamentEntity) -> Unit) : ListAdapter<TournamentEntity, LeagueAdapter.LeagueViewHolder>(LeagueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val binding = ItemLeagueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeagueViewHolder(binding, onLeagueClick)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LeagueViewHolder(
        private val binding: ItemLeagueBinding,
        private val onLeagueClick: (TournamentEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tournament: TournamentEntity) {
            binding.twLeagueName.text = tournament.name
            Glide.with(binding.iwLeagueLogo.context)
                .load(tournament.logoUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_error))
                .into(binding.iwLeagueLogo)

            binding.root.setOnClickListener {
                onLeagueClick(tournament)
            }
        }
    }

    class LeagueDiffCallback : DiffUtil.ItemCallback<TournamentEntity>() {
        override fun areItemsTheSame(oldItem: TournamentEntity, newItem: TournamentEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TournamentEntity, newItem: TournamentEntity): Boolean {
            return oldItem == newItem
        }
    }
}
