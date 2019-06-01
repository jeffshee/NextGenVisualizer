package io.github.jeffshee.visualizerdemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import io.github.jeffshee.visualizer.painters.fft.FftBar
import io.github.jeffshee.visualizer.painters.fft.FftWave
import io.github.jeffshee.visualizer.painters.fft.FftWaveRgb
import io.github.jeffshee.visualizer.painters.misc.SimpleIcon
import io.github.jeffshee.visualizer.utils.Preset
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var helper: VisualizerHelper
    private lateinit var bitmap: Bitmap
    private lateinit var circleBitmap: Bitmap

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
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.chino512)
        circleBitmap = SimpleIcon.getCircledBitmap(bitmap)

        helper = VisualizerHelper(0)
        visual.setPainterList(
            helper, listOf(
                //Preset.getPresetWithIcon("cWaveRgbIcon", bitmap)
                Preset.getPresetWithIcon("cIcon", bitmap)
            )
        )
    }

    override fun onDestroy() {
        helper.release()
        super.onDestroy()
    }
}
