package io.github.jeffshee.visualizer.utils

import android.graphics.Bitmap
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.painters.fft.FftBar
import io.github.jeffshee.visualizer.painters.fft.FftCircle
import io.github.jeffshee.visualizer.painters.fft.FftCircleWaveRgb
import io.github.jeffshee.visualizer.painters.misc.SimpleIcon
import io.github.jeffshee.visualizer.painters.modifier.Beat
import io.github.jeffshee.visualizer.painters.modifier.Rotate

class Preset {
    companion object{

        /**
         * Feel free to add your awesome preset here ;)
         * Hint: You can use `Group` painter to group multiple painters together as a single painter
         */
        fun getPreset(name: String): Painter{
            return when(name){
                "debug" -> FftBar()
                else -> FftBar()
            }
        }

        fun getPresetWithIcon(name: String, bitmap: Bitmap): Painter{
            return when(name){
                "debug" -> SimpleIcon(bitmap)
                "cIcon" -> Beat(listOf(Rotate(FftCircle()), SimpleIcon(SimpleIcon.getCircledBitmap(bitmap))))
                "cWaveRgbIcon" -> Beat(listOf(Rotate(FftCircleWaveRgb()), SimpleIcon(SimpleIcon.getCircledBitmap(bitmap))))
                else -> SimpleIcon(bitmap)
            }
        }
    }
}