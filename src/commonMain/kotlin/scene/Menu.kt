package scene

import ConfigModule
import com.soywiz.kds.iterators.fastForEachReverse
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.TtfFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openSync
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing
import gameplay.Alien
import gameplay.CHARACTER

class Menu : Scene() {

    var headImage: Image? = null
    var goImage: Image? = null
    lateinit var goBitmp: Bitmap

    lateinit var image: Image
    var aliens = ArrayList<Alien>()
    var selectRunAlien: Alien? = null
    lateinit var ttfFont: TtfFont
    lateinit var fontBitmap: Bitmap
    lateinit var tapString: Image
    var statToGo = false

    override suspend fun Container.sceneInit() {
        ttfFont = TtfFont(resourcesVfs["NotoSans-Black.ttf"].readAll().openSync())

        image = image(resourcesVfs["menu.png"].readBitmap()) {
            anchor(0.5, 0.5)
            scale(ConfigModule.size.width / width, ConfigModule.size.height / height)
            position(scaledWidth / 2, scaledHeight / 2)
        }

        goBitmp = resourcesVfs["go.png"].readBitmap()

        val greenAlien = Alien().apply {
            load(CHARACTER.GREEN)
            onClick {
                selectRunAlien?.stop()
                selectRunAlien = this
                walk()
            }
        }
        val purpleAlien = Alien().apply {
            load(CHARACTER.PURPLE)
            onClick {
                selectRunAlien?.stop()
                selectRunAlien = this
                walk()
            }
        }
        val pinkAlien = Alien().apply {
            load(CHARACTER.PINK)
            onClick {
                selectRunAlien?.stop()
                selectRunAlien = this
                walk()
            }
        }
        val beigeAlien = Alien().apply {
            load(CHARACTER.BEIGE)
            onClick {
                selectRunAlien?.stop()
                selectRunAlien = this
                walk()
            }
        }
        val yellowAlien = Alien().apply {
            load(CHARACTER.YELLOW)
            onClick {
                selectRunAlien?.stop()
                selectRunAlien = this
                walk()
            }
        }
        aliens.addAll(listOf(greenAlien, purpleAlien, pinkAlien, beigeAlien, yellowAlien))

        val bg = this
        var totalAlienWidth = 0.0
        aliens.fastForEachReverse {
            totalAlienWidth += it.width
            addChild(it)
        }

        var alienSpace = (bg.width - totalAlienWidth) / (aliens.count() + 1)
        for (i in 0 until aliens.count()) {
            aliens[i].apply {
                when (i) {
                    0 -> {
                        aliens[i].alignBottomToBottomOf(bg)
                        aliens[i].alignLeftToLeftOf(bg)
                        y -= 42
                    }
                    else -> {
                        aliens[i].alignBottomToBottomOf(aliens[i - 1])
                        aliens[i].alignLeftToRightOf(aliens[i - 1])
                    }
                }
                x += alienSpace
            }
        }

        val textString = "Choose An Alien to GO"
        val fontSize = 40.0
        fontBitmap = NativeImage(width = bg.width.toInt(), height = 100).apply {
            getContext2d().fillText("Choose An Alien to GO",
                    x = 0,
                    y = fontSize,
                    font = ttfFont,
                    fontSize = fontSize.toDouble(),
                    color = Colors.BLUE)
        }
        tapString = image(fontBitmap) {
            position((image.scaledWidth) / 2 - (textString.count() * fontSize) / 4, 50.0)
        }


        addUpdater {
            selectRunAlien?.also {
                Storage.SELECT_RUN_ALIEN = it.character

                if (headImage == null) {
                    headImage = image(it.headBitmap) {
                        anchor(0.5, 0.5)
                        alignTopToBottomOf(tapString)
                        centerXOn(bg)
                        scale = 1.5
                        x -= 200
                        y += 10

                    }
                    goImage = image(goBitmp) {
                        anchor(0.5, 0.5)
                        alignTopToBottomOf(tapString)
                        alignLeftToRightOf(headImage!!)
                        centerXOn(bg)
                        y += 10

                        onClick {
                            statToGo = true
                            launch {
                                delay(1.seconds)
                                sceneContainer.changeTo<GamePlay>() }
                        }

                        launch {
                            while (true) {
                                delay(200.milliseconds)
                                tween(this::rotation[(-5).degrees], time = 100.milliseconds, easing = Easing.EASE_IN_OUT)
                                tween(this::rotation[(5).degrees], time = 100.milliseconds, easing = Easing.EASE_IN_OUT)
                            }
                        }
                    }

                } else {
                    headImage!!.bitmap = it.headBitmap.slice()
                }
            }
        }


    }

    override suspend fun Container.sceneMain() {
        addHrUpdater {
            if(statToGo) {
                selectRunAlien?.also {
                    it.x+= 6
                }
            }
        }

    }
}
