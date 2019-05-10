package io.github.jeffshee.visualizer.painters.misc

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class BeatIcon(
    var bitmap: Bitmap,
    var startHz: Int = 60,
    var endHz: Int = 800,
    var xR: Float = .5f,
    var yR: Float = .5f,
    var baseR: Float = .3f,
    var ampR: Float = .3f,
    var peak: Float = 200f
) : Painter() {

    private val matrix = Matrix()
    private val circle = GravityModel(0f)

    companion object {
        fun getCircledBitmap(bitmap: Bitmap): Bitmap {
            val tmpBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val tmpCanvas = Canvas(tmpBitmap)
            val tmpPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            val tmpRect = Rect(0, 0, bitmap.width, bitmap.height)
            tmpCanvas.drawARGB(0, 0, 0, 0)
            tmpCanvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, bitmap.width / 2f, tmpPaint)
            tmpPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            tmpCanvas.drawBitmap(bitmap, tmpRect, tmpRect, tmpPaint)
            return tmpBitmap
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        bitmap.apply bitmap@{
            val fft = helper.getFftMagnitudeRange(startHz, endHz)
            circle.update(canvas.width * (baseR + getEnergy(fft).toFloat() / peak * ampR))
            val radius = circle.height
            matrix.apply {
                postScale(radius / this@bitmap.width, radius / this@bitmap.width)
                postTranslate(-radius / 2f, -radius / 2f)
            }
            drawHelper(canvas, "a", xR, yR) {
                canvas.drawBitmap(this, matrix, null)
            }
            matrix.reset()
        }
    }
}