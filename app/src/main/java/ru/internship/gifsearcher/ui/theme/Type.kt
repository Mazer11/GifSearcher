package ru.internship.gifsearcher.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.internship.gifsearcher.R

val Roboto = FontFamily.Default
val MontserratAlternates = FontFamily(
    Font(R.font.montserratalternates_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.montserratalternates_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.montserratalternates_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.montserratalternates_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.montserratalternates_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.montserratalternates_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.montserratalternates_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.montserratalternates_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.montserratalternates_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.montserratalternates_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserratalternates_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.montserratalternates_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.montserratalternates_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.montserratalternates_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.montserratalternates_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.montserratalternates_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.montserratalternates_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserratalternates_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )
)