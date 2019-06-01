package io.github.jeffshee.visualizer.painters.misc

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class SimpleIcon(
    var bitmap: Bitmap,
    var xR: Float = .5f,
    var yR: Float = .5f,
    var baseR: Float = .3f
) : Painter() {

    private val matrix = Matrix()

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
            val radius = canvas.width * baseR
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
