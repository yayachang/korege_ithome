package gameplay

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image

abstract class Item : Container() {
    var sprite: Image? = null
    var moveSpeed = 4
    var defaultX = 0.0
    abstract suspend fun load()
    abstract suspend fun setOffset(baseWidth: Double, baseHeight: Double)

    fun move() {
        x -= moveSpeed
    }

    fun stop(){
        moveSpeed = 0
    }
}