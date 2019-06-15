package io.github.jeffshee.visualizer.painters.misc

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.max

class Background(
    var bitmap: Bitmap,
    var xR: Float = .5f,
    var yR: Float = .5f,
    var wR: Float = 1f
) : Painter() {

    private val matrix = Matrix()

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        bitmap.apply bitmap@{
            matrix.apply {
                val scale = max(
                    canvas.width.toFloat() * wR / this@bitmap.width,
                    canvas.height.toFloat() * wR / this@bitmap.height
                )
                postScale(scale, scale)
                postTranslate(-scale * this@bitmap.width / 2f, -scale * this@bitmap.height.toFloat() / 2f)
            }
            drawHelper(canvas, "a", xR, yR) {
                canvas.drawBitmap(this, matrix, null)
            }
            matrix.reset()
        }
    }
}
