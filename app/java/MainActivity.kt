package com.example.dailyinspiration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyinspiration.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.formatter.ValueFormatter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pieChart: PieChart
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("challengePrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pieChart = binding.pieChart
        loadChartData()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_daily_challenge -> {
                    startActivity(Intent(this, ChallengeActivity::class.java))
                    true
                }
                R.id.nav_tracking -> {
                    startActivity(Intent(this, TrackingActivity::class.java))
                    true
                }
                R.id.nav_share -> {
                    shareApp()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadChartData() {
        val totalChallenges = 30
        val completedChallenges = (0 until totalChallenges).count {
            sharedPreferences.getBoolean("challenge_$it", false)
        }
        val remainingChallenges = totalChallenges - completedChallenges

        val completedPercentage = (completedChallenges.toFloat() / totalChallenges) * 100
        val remainingPercentage = 100 - completedPercentage

        val entries = listOf(
            PieEntry(completedPercentage, "Completed"),
            PieEntry(remainingPercentage, "Remaining")
        )

        val dataSet = PieDataSet(entries, "Challenges")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()
        dataSet.valueTextSize = 16f
        dataSet.valueFormatter = CustomPercentFormatter()

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.centerText = "Challenge Completion"
        pieChart.animateY(1000)
        pieChart.invalidate()
    }


    private fun shareApp() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "אפליקציית ההשראה היומית שלי! הצטרפו גם אתם לאתגרי היום.")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "שתף את האפליקציה"))
    }

    class CustomPercentFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return String.format("%.1f%%", value)
        }
    }
}
