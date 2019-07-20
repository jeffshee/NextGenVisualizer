# [Beta] NextGenVisualizer
Audio visualizer library for Android. Written in Kotlin. Aim for:
> Light-weight, Flexible, Easy.

## Preview
Tested on Huawei MediaPad M3, performance was around 50~60 fps. (It depends)
### Gif
> (Basic) Waveform, FftBar, FftLine, FftWave, FftWaveRgb

![](/preview/01.gif "")

> (Basic) FftCircle, FftCircleWave, FftCircleWaveRgb

![](/preview/02.gif "")

> Glitch+Beat+Rotate, SimpleIcon, FftCircle

![](/preview/03.gif "")

> Shake, SimpleIcon, FftCircleWaveRgb, Waveform

![](/preview/04.gif "")

> Shake, Background, FftCircle

![](/preview/05.gif "")

### Video
You can watch it on my [Google Drive](https://drive.google.com/open?id=1x6vJIhzd4OsF8EHsRRLp_WZzLGJSMkhp). Full-screen+HD recommended!

☆ I don't own the music, icon and wallpaper. For demonstration purpose only.

## Please!! (｡>ｕ<｡)
While the library isn't quite ready yet, feel free to open an issue if you have any problem/suggestion :heart:

Star my project if you like it! :star2:

Stay tune!!

## Parameter's Naming Convention
Just for reference ＼(;ﾟ∇ﾟ)/　Some of them might not accurate, but you will get the idea.

0. bitmap = Bitmap class. Doesn't have a default param value.
0. text = String. "" by default.
0. painter = Painter class. Doesn't have a default param value.
0. painters = List of Painter class. Doesn't have a default param value.

1. paint = Paint class. Lots of customization could be done here.
	For example, change color, style, stroke_width ...

2. startHz = Start Frequency
3. endHz = End Frequency

4. num = Number of Bars/Bands; In case of "Wave", number of slices. 
	(The more slices, the better quality of the curves = computational heavier)
5. interpolator = The method of interpolation. "li" for Linear, "sp" for Spline.
6. side = The side where the drawing should appear. 
	"a" for Side A (= up/out), "b" for Side B (= down/in), "ab" for Both Side

7. mode = *Might be changed in the future. Mode. "mirror" for Mirror mode.

8. xR = The ratio of X position to the canvas.width (= the width of the visualizer's view)
	For example, 0f mean the start/left, while 1f mean the end/right of the screen.
9. yR = The ratio of Y position to the canvas.height (= the height of the visualizer's view)
	For example, 0f mean the top, while 1f mean the bottom of the screen.
10. wR = The ratio of width to the canvas.width
11. hR = The ratio of height to the canvas.height

11. X = *Change confirmed. The X position
11. Y = *Change confirmed. The Y position

12. gapX = *Might be changed in the future. The width of the gap.
13. baseR = *Might be changed in the future. The ratio of radius to the canvas.width

14. ampR = The rate of amplification. 1f by default.

15. beatAmpR = *Might be changed in the future. The rate of amplification of beat.
16. peak = *Might be changed in the future. The threshold of beat.
17. rpm = Rotation per minute.

18. enableBoost = *Might be changed in the future. To use "Power Spectrum" instead.
