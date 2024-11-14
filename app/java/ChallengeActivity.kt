package com.example.dailyinspiration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyinspiration.databinding.ActivityChallengeBinding

class ChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChallengeBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private val sharedPreferences by lazy { getSharedPreferences("challenge_prefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isCompleted = sharedPreferences.getBoolean("is_challenge_completed", false)
        binding.completedCheckBox.isChecked = isCompleted

        observeViewModel()

        binding.shareButton.setOnClickListener {
            shareChallenge()
        }

        binding.returnToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            saveChallengeCompletionStatus(isChecked)
        }
    }

    private fun observeViewModel() {
        challengeViewModel.quoteText.observe(this) { quote ->
            binding.quoteTextView.text = quote
        }

        challengeViewModel.challengeText.observe(this) { challenge ->
            binding.dailyChallengeTextView.text = challenge
        }
    }

    private fun shareChallenge() {
        val quote = binding.quoteTextView.text.toString()
        val challenge = binding.dailyChallengeTextView.text.toString()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$quote\n\n$challenge")
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "שתף את האתגר"))
    }

    private fun saveChallengeCompletionStatus(isCompleted: Boolean) {
        sharedPreferences.edit().putBoolean("is_challenge_completed", isCompleted).apply()
        val message = if (isCompleted) "אתגר הושלם!" else "אתגר לא הושלם."
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
