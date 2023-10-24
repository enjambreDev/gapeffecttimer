package dev.enjambre.gapeffecttimer.ui

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ToneGenerator
import android.os.CountDownTimer
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.lifecycle.ViewModel
import dev.enjambre.gapeffecttimer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil
import kotlin.math.roundToLong
import kotlin.random.Random

class GapEffectTimerViewModel: ViewModel() {

    private val minTimeInSeconds = 100L
    private val maxAddedTimeInSeconds = 40L
    private val restTimeInSeconds = 10L
    private val countDownIntervalInMillis = 100L

    private var _initialTimeForThisTurn: MutableStateFlow<Long> = MutableStateFlow(minTimeInSeconds)
    var initialTimeForThisTurn: StateFlow<Long> = _initialTimeForThisTurn

    private var _timeLeft: MutableStateFlow<Long> = MutableStateFlow(minTimeInSeconds)
    var timeLeft: StateFlow<Long> = _timeLeft

    private var _randomTimerTurn: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var randomTimerTurn: StateFlow<Boolean> = _randomTimerTurn

    private var _loopNumber: MutableStateFlow<Int> = MutableStateFlow(1)
    var loopNumber: StateFlow<Int> = _loopNumber

    private val _beep: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var beep: StateFlow<Boolean> = _beep

    private lateinit var randomTimer: CountDownTimer

    init {
        setRandomTimer()
    }

    private var restTimer: CountDownTimer = object: CountDownTimer(restTimeInSeconds * 1000, countDownIntervalInMillis) {

        override fun onTick(millisUntilFinished: Long) {
            _timeLeft.update { ceil(millisUntilFinished.toDouble().div(1_000)).roundToLong() }
        }

        override fun onFinish() {
            _beep.update { true }
            setRandomTimer()
            startRandomTimer()
            _randomTimerTurn.update { true }
        }
    }

    fun startRandomTimer() {
        randomTimer.start()
    }

    private fun setRandomTimer() {
        val randomTime = getRandomTimeInSeconds()
        _initialTimeForThisTurn.update { randomTime }
        Log.d("Setting random timer", "$randomTime")
        _timeLeft.update { randomTime }
        randomTimer = object: CountDownTimer(randomTime * 1000L, countDownIntervalInMillis) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.update { ceil(millisUntilFinished.toDouble().div(1_000)).roundToLong() }
            }

            override fun onFinish() {
                _beep.update { true }
                _loopNumber.update { _loopNumber.value + 1 }
                restTimer.start()
                _randomTimerTurn.update { false }
                _initialTimeForThisTurn.update { restTimeInSeconds }
            }
        }
    }

    fun stopTimers() {
        randomTimer.cancel()
        restTimer.cancel()
        setRandomTimer()
    }

    private fun getRandomTimeInSeconds(): Long {
        val random = Random(System.currentTimeMillis())
        val randomTime = minTimeInSeconds + random.nextLong(maxAddedTimeInSeconds)
        Log.d("RANDOM TIME HERE LOOK OUT", "$randomTime")
        return randomTime
    }

    fun onBeep() {
        //sin update porque no queremos recomposition
        _beep.value = false
    }
}