package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.random.Random

class Glitch : Painter {

    var painters: List<Painter>
    //
    var startHz: Int
    var endHz: Int
    //
    var peak: Float = 50f
    var duration: Int = 200

    override var paint = Paint()

    constructor(
        painters: List<Painter>, startHz: Int = 60, endHz: Int = 300
    ) {
        this.painters = painters
        this.startHz = startHz
        this.endHz = endHz
    }

    constructor(
        painter: Painter, startHz: Int = 60, endHz: Int = 300
    ) : this(
        listOf(painter), startHz, endHz
    )

    private val energy = GravityModel(0f)
    private var count = 0

    override fun calc(helper: VisualizerHelper) {
        energy.update(helper.getFftMagnitudeRange(startHz, endHz).average().toFloat())
        if (energy.height > peak) count = (duration / 1000f * 60f).toInt()
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        if (count > 0) {
            val width = canvas.width.toFloat()

            val y = Random.nextFloat() * canvas.height
            val h = Random.nextFloat() * 200f + 100f
            val displacement = Random.nextFloat() * .1f - .05f
            val noise = Random.nextFloat() * .1f - .05f

            canvas.save()
            canvas.clipRect(0f, y, width, y + h, Region.Op.DIFFERENCE)
            drawHelper(canvas, "a", 0f, 0f) {
                painters.forEach { painter ->
                    painter.paint.apply {
                        colorFilter = null;xfermode = null
                    }
                    painter.draw(canvas, helper)
                }
            }
            canvas.restore()

            canvas.save()
            canvas.clipRect(0f, y, width, y + h)
            drawHelper(canvas, "a", displacement - noise, 0f) {
                painters.forEach { painter ->
                    painter.paint.apply {
                        colorFilter = LightingColorFilter(Color.RED, Color.BLACK)
                        ;xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
                    }
                    painter.draw(canvas, helper)
                }
            }
            drawHelper(canvas, "a", displacement, 0f) {
                painters.forEach { painter ->
                    painter.paint.apply {
                        colorFilter = LightingColorFilter(Color.GREEN, Color.BLACK)
                        ;xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
                    }
                    painter.draw(canvas, helper)
                }
            }
            drawHelper(canvas, "a", displacement + noise, 0f) {
                painters.forEach { painter ->
                    painter.paint.apply {
                        colorFilter = LightingColorFilter(Color.BLUE, Color.BLACK)
                        ;xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
                    }
                    painter.draw(canvas, helper)
                }
            }
            canvas.restore()
            count--
        } else {
            painters.forEach { painter ->
                painter.paint.apply {
                    colorFilter = null;xfermode = null
                }
                painter.draw(canvas, helper)
            }
        }


    }
}