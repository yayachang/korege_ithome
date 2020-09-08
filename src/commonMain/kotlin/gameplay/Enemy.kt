package gameplay

import com.soywiz.klock.seconds
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korge.view.sprite
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Enemy : Item() {

    lateinit var spriteMap: Bitmap
    lateinit var walkAnimation: SpriteAnimation
    lateinit var walkSprite: Sprite

    override suspend fun load() {
        spriteMap = resourcesVfs["pink_enemy_walk.png"].readBitmap()
        walkAnimation = SpriteAnimation(
                spriteMap = spriteMap,
                spriteWidth = 51,
                spriteHeight = 28,
                marginTop = 0,
                marginLeft = 0,
                columns = 2,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0
        )

        walkSprite = sprite(walkAnimation) {
            spriteDisplayTime = 0.1.seconds
        }


        walkSprite.playAnimationLooped()
    }
}