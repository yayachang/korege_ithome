package scene

import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
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
        alien.x = ItemManager.BASE_WIDTH * -1
        alien.addUpdater {
            if(collidesWithShape(ItemManager.scoreItem)){
                score.plus(1)
            }
            if(collidesWithShape(ItemManager.hurtItem)){
                if(hurt()) {
                    blood.minus()
                }
            }
        }
        ItemManager.setCollision(alien, this)

    }

    override suspend fun Container.sceneMain() {
        onClick {
            alien.jump()
        }

        keys {
            down(Key.SPACE) {
                alien.jump()
                println("Key down")
            }
            up(Key.SPACE) {
                println("Key up")
            }
        }

        addHrUpdater {
            if (alien.x <= ItemManager.BASE_WIDTH) {
                alien.x += 3
            }
        }

        delay(1000.milliseconds)

        gameTimer.start()
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
            checkGameOver()
        }
    }

    suspend fun loadCharacter(): Alien {
        return Alien().apply {
            load(SharedData.SELECT_RUN_ALIEN)
            walk()
        }
    }

    fun checkGameOver(){
        if(blood.nowValue == 0){
            background.stop()
            ItemManager.stop()
            gameTimer.stop()
            alien.dead()
            SharedData.run {
                GAME_SCORE = score.nowValue
                IS_GAME_OVER_SUCCES = false
            }
            goToGameOver()
        }else if(gameTimer.totalTime == 0){
            background.stop()
            ItemManager.stop()
            alien.goal()
            SharedData.run {
                GAME_SCORE = score.nowValue
                IS_GAME_OVER_SUCCES = true
            }
            goToGameOver()
        }
    }

    fun goToGameOver() {
        launchImmediately {
            delay(2.seconds)
            sceneContainer.changeTo<GameOver>()
        }
    }

}
