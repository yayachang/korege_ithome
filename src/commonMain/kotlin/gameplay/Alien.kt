package gameplay

import com.soywiz.klock.seconds
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korge.view.sprite
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

enum class STATUS {
    WALK,
    JUMP,
    FALL,
    HURT,
    DEAD,
    GOAL
}

class Alien : Container() {

    var status = STATUS.WALK
    lateinit var spriteMap: Bitmap
    lateinit var walkAnimation: SpriteAnimation
    lateinit var sprite: Sprite
    lateinit var jumpBitmap: Bitmap

    var defaultHeight = 0.0

    suspend fun load() {
        spriteMap = resourcesVfs["green_alien_walk.png"].readBitmap()
        walkAnimation = SpriteAnimation(
                spriteMap = spriteMap,
                spriteWidth = 72,
                spriteHeight = 97,
                marginTop = 0,
                marginLeft = 0,
                columns = 11,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0
        )
        jumpBitmap = resourcesVfs["green_alien_jump.png"].readBitmap()
    }

    fun walk() {
        status = STATUS.WALK
        sprite = sprite(walkAnimation) {
                spriteDisplayTime = 0.1.seconds
            }.apply {
                playAnimationLooped()
        }
    }

    fun changeStatus(){
        sprite.stopAnimation()
        sprite.removeFromParent()
    }

    fun jump() {
        if (status != STATUS.JUMP && status != STATUS.FALL) {
            changeStatus()
            sprite = sprite(jumpBitmap)
            status = STATUS.JUMP
        }
    }

    fun fall() {
        status = STATUS.FALL
    }

    fun update() {
        when (status) {
            STATUS.JUMP -> {
                y = y - 4
                if (y <= defaultHeight - 100) {
                    fall()
                }
            }
            STATUS.FALL -> {
                y = y + 6
                if (y >= defaultHeight) {
                    changeStatus()
                    walk()
                }
            }
        }
    }

}
