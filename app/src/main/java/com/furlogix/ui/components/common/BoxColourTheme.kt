package com.furlogix.ui.components.common

import androidx.compose.ui.graphics.Color

class BoxColourTheme {
    companion object{

        private val colourList = listOf(
            Color(0xff1abbc3),
            Color(0xff6222a2),
        )

        fun GetColour(index : Int) : Color{
            return colourList[index%2]
        }
    }
}