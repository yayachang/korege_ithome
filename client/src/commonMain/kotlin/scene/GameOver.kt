package scene

import com.soywiz.klock.seconds
import com.soywiz.korge.input.InputKeys
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.time.delay
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.TtfFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openSync
import gameplay.Alien
import gameplay.CHARACTER
import gameplay.ItemManager
import gameplay.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GameOver(
) : Scene() {

    lateinit var ttfFont: TtfFont
    lateinit var fontBitmap: Bitmap
    lateinit var resultString: Image
    lateinit var image: Image

    override suspend fun Container.sceneInit() {
        ttfFont = TtfFont(resourcesVfs["NotoSans-Black.ttf"].readAll().openSync())


        if(SharedData.IS_GAME_OVER_SUCCES){
            image = image(resourcesVfs["gameover_success.png"].readBitmap()) {
                anchor(0.5, 0.5)
                scale(ConfigModule.size.width / width, ConfigModule.size.height / height)
                position(scaledWidth / 2, scaledHeight / 2)
            }
        }else{
            image = image(resourcesVfs["gameover_failed.png"].readBitmap()) {
                anchor(0.5, 0.5)
                scale(ConfigModule.size.width / width, ConfigModule.size.height / height)
                position(scaledWidth / 2, scaledHeight / 2)
            }
        }

        val textString = if(SharedData.IS_GAME_OVER_SUCCES){"You're Success!!!"}else{"You're Failure!!"}
        val fontSize = 40.0
        fontBitmap = NativeImage(width = width.toInt(), height = 100).apply {
            getContext2d().fillText(textString,
                    x = 0,
                    y = fontSize,
                    font = ttfFont,
                    fontSize = fontSize.toDouble(),
                    color = Colors.BLUE)
        }
        resultString = image(fontBitmap) {
            position((image.scaledWidth) / 2 - (textString.count() * fontSize) / 4, 50.0)
        }


        CoroutineScope(Dispatchers.Unconfined).launch{

            //加入分數
            val score = Score().apply { load() }
            addChild(score)
            score.initPosition()
            score.alignTopToBottomOf(resultString)
            score.nowValue = SharedData.GAME_SCORE
            score.update()

            //加上外星人
            val alien = Alien().apply { load(SharedData.SELECT_RUN_ALIEN)}
            addChild(alien)
            alien.alignTopToBottomOf(resultString)
            alien.alignRightToLeftOf(score)
            alien.x -=30
            if(SharedData.IS_GAME_OVER_SUCCES){
                alien.goal()
            }else{
                alien.hurt()
            }
            //加上下一步按鈕
            image( resourcesVfs["next.png"].readBitmap()) {
                anchor(0.5, 0.5)
                position(image.width / 2, image.height - 200)
                onClick {
                    launchImmediately {
                        sceneContainer.changeTo<Rank>()
                    }
                }
            }
        }
    }
}
