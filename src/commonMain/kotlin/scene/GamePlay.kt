package scene

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.addHrUpdater
import com.soywiz.korge.view.alignBottomToTopOf
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launchImmediately
import gameplay.*

class GamePlay : Scene() {
    lateinit var alien: Alien
    lateinit var background: Background
    lateinit var blood: Blood
    lateinit var score: Score
    lateinit var gameTimer: GameTimer

    override suspend fun Container.sceneInit() {

        //加入背景
        background = Background().apply { load() }
        addChild(background)

        //加入血條
        blood = Blood().apply { load() }
        addChild(blood)
        blood.initPosition()
        blood.update()

        //加入分數
        score = Score().apply { load() }
        addChild(score)
        score.initPosition()
        score.update()

        //加入倒數計時
        gameTimer = GameTimer().apply { load() }
        addChild(gameTimer)
        gameTimer.initPosition()

        //加入地板、障礙物、敵人
        ItemManager.init()
        ItemManager.load(this)

        //加入外星人
        alien = loadCharacter()
        addChild(alien)
        alien.alignBottomToTopOf(ItemManager.BASE_FLOOR!!)
        alien.defaultHeight = alien.y
        alien.x = ItemManager.BASE_WIDTH * 1

    }

    override suspend fun Container.sceneMain() {

        keys {
            down(Key.SPACE) {
                alien.jump()
                println("Key down")
            }
            up(Key.SPACE) {
                println("Key up")
            }
        }

        delay(1000.milliseconds)

        addFixedUpdater(1.seconds){
            gameTimer.minus()
        }

        addHrUpdater {
            background.move()
            ItemManager.move()
            alien.update()
            blood.update()
            score.update()
            gameTimer.update()
        }
    }

    suspend fun loadCharacter(): Alien {
        return Alien().apply {
            load()
            walk()
        }
    }

    suspend fun loadFloor() {
        /*for (i in 0..14) {
            floorList.add(
                    Floor(this.sceneView).load().run{
                        position(i * scaledWidth, ConfigModule.size.height - scaledHeight)
                    })
        }*/
    }

    fun goToGameOver() {
        launchImmediately { sceneContainer.changeTo<GameOver>() }
    }

}
