package dev.enjambre.gapeffecttimer.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.enjambre.gapeffecttimer.R

@Composable
fun TimerScreen(gapEffectTimerViewModel: GapEffectTimerViewModel) {

    val initialTimeForThisTurn = gapEffectTimerViewModel.initialTimeForThisTurn.collectAsState()
    val timeLeftState = gapEffectTimerViewModel.timeLeft.collectAsState()
    var isTimerCounting by rememberSaveable { mutableStateOf(false) }

    val randomTimerTurn = gapEffectTimerViewModel.randomTimerTurn.collectAsState()
    val loopNumber = gapEffectTimerViewModel.loopNumber.collectAsState()

    var beep = gapEffectTimerViewModel.beep.collectAsState()

    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.mouse_click)

    if (beep.value) {
        gapEffectTimerViewModel.onBeep()
        mediaPlayer.start()
    }
    Column(
        modifier = Modifier
            .fillMaxSize().clickable {
                if (!isTimerCounting) {
                    gapEffectTimerViewModel.startRandomTimer()
                } else {
                    gapEffectTimerViewModel.stopTimers()
                }
                isTimerCounting = !isTimerCounting
            }
    ) {
        CircularProgressIndicator(
            progress = timeLeftState.value.toFloat().div(initialTimeForThisTurn.value),
            strokeWidth = 4.dp,
            modifier = Modifier.fillMaxWidth().padding(32.dp)
        )
        Text(
            text = timeLeftState.value.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text =
            if (randomTimerTurn.value) {
                "Random Timer ${loopNumber.value}"
            } else {
                "Rest Timer"
            },
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = if (isTimerCounting) "Cancel" else "Start",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 128.dp)
        )
    }
}