package com.example.vetapp.ui.componets.graphs

import androidx.compose.ui.graphics.Color

class ColourList {

    companion object{
        private val colourList = listOf(
            Color.Red,
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Magenta,
        )

        private var i = 0

        fun getNext() : Color{
            i += 1
            if(i == colourList.size){
                i = 0
            }
            return colourList[i]
        }
    }

}