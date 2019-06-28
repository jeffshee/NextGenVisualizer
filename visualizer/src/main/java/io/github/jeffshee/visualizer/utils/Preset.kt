package io.github.jeffshee.visualizer.utils

import android.graphics.*
import io.github.jeffshee.visualizer.painters.*
import io.github.jeffshee.visualizer.painters.fft.*
import io.github.jeffshee.visualizer.painters.misc.*
import io.github.jeffshee.visualizer.painters.modifier.*

class Preset {
    companion object {

        /**
         * Feel free to add your awesome preset here ;)
         * Hint: You can use `Compose` painter to group multiple painters together as a single painter
         */
        fun getPreset(name: String): Painter {
            return when (name) {
                "debug" -> FftBar()
                else -> FftBar()
            }
        }

        fun getPresetWithBitmap(name: String, bitmap: Bitmap): Painter {
            return when (name) {
                "cIcon" -> Compose(Rotate(FftCircle()), SimpleIcon(SimpleIcon.getCircledBitmap(bitmap)))
                "cWaveRgbIcon" -> Compose(
                    Rotate(FftCircleWaveRgb()),
                    SimpleIcon(SimpleIcon.getCircledBitmap(bitmap)))
                "liveBg" -> Scale(Shake(Background(bitmap)), scaleX = 1.02f, scaleY = 1.02f)
                "debug" -> SimpleIcon(bitmap)
                else -> SimpleIcon(bitmap)
            }
        }
    }
}