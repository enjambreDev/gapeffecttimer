package dev.enjambre.gapeffecttimer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.enjambre.gapeffecttimer.R

val RobotoMono: FontFamily = FontFamily(Font(R.font.robotomono))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RobotoMono,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = RobotoMono,
        fontSize = 120.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoMono,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoMono,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp
    )
)