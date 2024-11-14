package com.example.dailyinspiration

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyinspiration.databinding.ItemChallengeBinding

class ChallengeAdapter(
    private val challenges: List<String>,
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    inner class ChallengeViewHolder(private val binding: ItemChallengeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: String, position: Int) {
            binding.challengeTitle.text = challenge

            val isCompleted = sharedPreferences.getBoolean("challenge_$position", false)
            binding.challengeCheckbox.isChecked = isCompleted

            binding.challengeCheckbox.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
                sharedPreferences.edit().putBoolean("challenge_$position", isChecked).apply()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChallengeBinding.inflate(inflater, parent, false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge, position)
    }

    override fun getItemCount(): Int = challenges.size
}
