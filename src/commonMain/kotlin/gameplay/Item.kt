package gameplay

import com.soywiz.korge.view.Container

abstract class Item : Container() {
    var moveSpeed = 4
    var defaultX = 0.0
    abstract suspend fun load()
    fun move() {
        x -= moveSpeed
    }
}