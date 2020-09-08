package scene

import com.soywiz.korev.Key
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.service.storage.NativeStorage
import com.soywiz.korge.service.storage.get
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.*
import com.soywiz.korio.async.launchImmediately

class Menu : Scene() {
    override suspend fun Container.sceneInit() {

        text("I'm in ${Menu::class.simpleName}") {
            position(128, 128)
        }

        textButton(256.0, 32.0) {
            text = "Go to GamePlay"
            position(128, 128 + 32)
            onClick {
                launchImmediately { sceneContainer.changeTo<GamePlay>() }
            }
        }


    }

    override suspend fun Container.sceneMain() {

        /*circle().mouse {
            move {
                println("circle x:${x} y:${y}")
            }
            click{
                println("click")

            }
        }

        val input = views.input
        text("").addUpdater {
            text = "Mouse Position:${input.mouse}"
            position(input.mouse.x, input.mouse.y)

        }

        text("").addUpdater {
            when {
                input.keys.justPressed(Key.SPACE) -> {
                    text = "Key justPressed"
                    println("Key justPressed")
                }
                input.keys.pressing(Key.SPACE) -> {
                    text = "Key pressing"
                    println("Key pressing")

                }
                input.keys.justReleased(Key.SPACE) -> {
                    text = "Key justReleased"
                    println("Key justReleased")
                }
            }
        }

        mouse{
            click{
                println("mouse click")
            }
            over{
                println("mouse over")
            }
            down{
                println("mouse down")
            }
            up{
                println("mouse up")
            }
        }*/

        /*keys{
            down(Key.SPACE){
            }
            up(Key.SPACE){
                println("Key up")
            }
        }*/

    }
}
