package scene

import ConfigModule
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korge.animate.animate
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.TtfFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openSync
import com.soywiz.korma.interpolation.Easing
import kotlinx.coroutines.Job

class Splash : Scene() {

    var job: Job? = null

    val textString = "Tap to Start"
    val fontSize = 30
    var initX = 0.0
    var initY = 0.0
    var moveHeight = 0.0

    lateinit var ttfFont:TtfFont
    lateinit var image:Image
    lateinit var fontBitmap: Bitmap
    lateinit var tapString: Image

    override suspend fun Container.sceneInit() {
        ttfFont = TtfFont(resourcesVfs["NotoSans-Black.ttf"].readAll().openSync())
        image = image(resourcesVfs["splash.png"].readBitmap()) {
            anchor(0.5, 0.5)
            scale(ConfigModule.size.width / width, ConfigModule.size.height / height)
            position(scaledWidth / 2, scaledHeight / 2)
        }
        fontBitmap = NativeImage(width = 200, height = 100).apply {
            getContext2d().fillText(textString,
                    x = 0,
                    y = fontSize,
                    font = ttfFont,
                    fontSize = fontSize.toDouble(),
                    color = Colors.BLUE)
        }
        initX = (image.scaledWidth) / 2 - (textString.count() * fontSize) / 4
        initY = 0.0
        moveHeight = (image.scaledHeight - image.scaledHeight / 4)
        tapString = image(fontBitmap) {
            position(initX, initY)
            onClick {
                job?.cancel()
                sceneContainer.changeTo<Menu>()
            }
        }
    }

    override suspend fun Container.sceneMain() {

        /*tapString.addFixedUpdater(100.milliseconds){
            tapString.alpha -= 0.1
            if(tapString.alpha <= 0){
                tapString.alpha = 1.0
            }
            if(tapString.y < moveHeight){
                tapString.y += 30
            }
        }*/
        /*while (true) {
            job = launchImmediately {
                animate(completeOnCancel = true) {

                }
            }
            job?.join()
        }*/
        job = launchImmediately {
            while (true) {
                animate(completeOnCancel = true) {
                    parallel {
                        tapString.moveToWithSpeed(initX, moveHeight, 300.0, Easing.EASE_IN_OUT)
                    }
                    parallel {
                        tapString.alpha(0.0, time = 1000.milliseconds, easing = Easing.LINEAR)
                        if (tapString.alpha == 0.0) {
                            tapString.alpha = 1.0
                        }
                    }
                }
                delay(1.seconds)
            }
        }
        job?.join()
    }
}