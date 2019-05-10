package io.github.jeffshee.visualizerdemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import io.github.jeffshee.visualizer.painters.fft.FftCircle
import io.github.jeffshee.visualizer.painters.fft.FftWaveRgb
import io.github.jeffshee.visualizer.painters.misc.BeatIcon
import io.github.jeffshee.visualizer.painters.waveform.Waveform
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var helper: VisualizerHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        } else init()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 0 && grantResults[0] == 0) init()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun init() {
        helper = VisualizerHelper(0)
        visual.setPainterList(
            helper, listOf(
                FftWaveRgb(),
                Waveform().apply { paint.color = Color.argb(100, 255, 255, 255)},
                BeatIcon(BeatIcon.getCircledBitmap(BitmapFactory.decodeResource(resources, R.drawable.chino512))),
                FftCircle().apply { paint.color = Color.argb(200, 255, 255, 255);paint.strokeWidth = 8f }
            )
        )
    }

    override fun onDestroy() {
        helper.release()
        super.onDestroy()
    }
}
