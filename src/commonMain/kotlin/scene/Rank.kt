package scene

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import com.soywiz.korio.async.launchImmediately

class Rank : Scene() {
    override suspend fun Container.sceneInit() {
        text("I'm in ${Rank::class.simpleName}") {
            position(128, 128)
        }
        textButton(256.0, 32.0) {
            text = "Go to Menu"
            position(128, 128 + 32)
            onClick {
                launchImmediately { sceneContainer.changeTo<Menu>() }
            }
        }
    }
}
