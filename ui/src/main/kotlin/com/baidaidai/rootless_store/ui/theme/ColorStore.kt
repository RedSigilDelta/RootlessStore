package com.baidaidai.rootless_store.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorBasic(
    val color: Color,
    val onColor: Color
)
@Immutable
data class ColorSet(
    val lightSet: ColorBasic,
    val darkSet: ColorBasic
)

@Composable
fun ColorFactory(
    colorSet: ColorSet
): ColorBasic{
    val isSystemInDarkTheme = isSystemInDarkTheme()

    return if (isSystemInDarkTheme){
        colorSet.darkSet
    }else{
        colorSet.lightSet
    }

}