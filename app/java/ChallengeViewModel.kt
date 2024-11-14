package com.example.dailyinspiration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChallengeViewModel : ViewModel() {

    private val _quoteText = MutableLiveData<String>()
    val quoteText: LiveData<String> = _quoteText

    private val _challengeText = MutableLiveData<String>()
    val challengeText: LiveData<String> = _challengeText

    init {
        _quoteText.value = "הציטוט היומי: אל תפסיק ללמוד."
        _challengeText.value = "האתגר היומי: קרא פרק אחד בספר שאתה אוהב."
    }

    fun updateQuote(newQuote: String) {
        _quoteText.value = newQuote
    }

    fun updateChallenge(newChallenge: String) {
        _challengeText.value = newChallenge
    }
}
