package scene

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import com.soywiz.korio.async.launchImmediately

class GameOver(
) : Scene() {
    suspend override fun Container.sceneInit() {

        text("I'm in ${GameOver::class.simpleName}"){
            position(128, 128)
        }

        textButton(256.0, 32.0) {
            text = "Go to Rank"
            position(128, 128 + 32)
            onClick {
                launchImmediately { sceneContainer.changeTo<Rank>() }
            }
        }
    }
}
