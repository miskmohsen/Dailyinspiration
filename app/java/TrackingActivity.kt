package com.example.dailyinspiration

import android.content.Intent
import android.os.Bundle
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyinspiration.databinding.ActivityTrackingBinding

class TrackingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackingBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("challengePrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        observeViewModel()

        binding.returnToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupRecyclerView() {
        val challengeList = listOf(
            "אתגר ראשון: עשה הליכה יומית",
            "אתגר שני: שתה 8 כוסות מים",
            "אתגר שלישי: קרא ספר 30 דקות",
            "אתגר רביעי: עשה מדיטציה 10 דקות",
            "אתגר חמישי: כתוב 3 דברים חיוביים שקרו היום",
            "אתגר שישי: הקדש זמן לשיחה עם חבר קרוב",
            "אתגר שביעי: התנתק מהמדיה החברתית לשעה",
            "אתגר שמיני: נסה משהו חדש במטבח",
            "אתגר תשיעי: הקשב לשיר שאתה אוהב בריכוז",
            "אתגר עשירי: בצע תרגילי נשימה להרגעת המוח",
            "אתגר אחד-עשר: כתוב ביומן על חוויה חיובית שהייתה לך",
            "אתגר שניים-עשר: סידר את החדר או המשרד שלך",
            "אתגר שלושה-עשר: הקדש 15 דקות לקריאה על נושא שמעניין אותך",
            "אתגר ארבעה-עשר: בצע תרגילים לשיפור הגמישות",
            "אתגר חמישה-עשר: צא החוצה לטיול קצר בפארק או בטבע",
            "אתגר שישה-עשר: תכנן את המטרות שלך לשבוע הקרוב",
            "אתגר שבעה-עשר: שלח הודעת תודה למישהו שעזר לך",
            "אתגר שמונה-עשר: צייר או ציירי ציור פשוט כדי להירגע",
            "אתגר תשעה-עשר: נהל תקשורת חיובית עם כל מי שתפגוש היום",
            "אתגר עשרים: בצע תרגיל פיזי פשוט, כמו שכיבות סמיכה או סקוואטים",
            "אתגר עשרים ואחד: צפה בסרט דוקומנטרי או סרטון למידה ביוטיוב",
            "אתגר עשרים ושניים: נסה להקדים בשעה לשינה הלילה",
            "אתגר עשרים ושלושה: צפה בזריחה או בשקיעה",
            "אתגר עשרים וארבעה: השתמש בטכניקות מיינדפולנס להפחתת לחצים",
            "אתגר עשרים וחמישה: מצא זמן לצפות בקומדיה קצרה כדי לצחוק",
            "אתגר עשרים ושישה: צפה בשמי הלילה ונסה לזהות כוכבים",
            "אתגר עשרים ושבעה: למד מילה חדשה בשפה זרה",
            "אתגר עשרים ושמונה: בצע מתיחה לכל שרירי הגוף למשך 10 דקות",
            "אתגר עשרים ותשעה: בקר במוזיאון וירטואלי או בתערוכה מקוונת",
            "אתגר שלושים: צפה בסרטון מוטיבציה כדי להעלות את מצב הרוח"
        )


        val challengeAdapter = ChallengeAdapter(challengeList, this, sharedPreferences)

        binding.challengesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@TrackingActivity)
            adapter = challengeAdapter
        }
    }

    private fun observeViewModel() {
        challengeViewModel.quoteText.observe(this) { quote ->
            binding.quoteTextView.text = quote
        }

        challengeViewModel.challengeText.observe(this) { challenge ->
            binding.challengeTextView.text = challenge
        }
    }
}