package gameplay

import com.soywiz.klock.seconds
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.vector.rect

class Enemy : Item() {

    val offsetX = 20.0
    val offsetY = 10.0

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

        hitShape {
            rect(walkSprite.width - offsetX, walkSprite.height - offsetY, offsetX / 2, (walkSprite.height + offsetY) / 2)
        }
        //solidRect(walkSprite.width - offsetX, walkSprite.height - offsetY, Colors.BLUE).xy(offsetX / 2, (walkSprite.height + offsetY) / 2)
    }

    override suspend fun setOffset(baseWidth: Double, baseHeight: Double) {
        y += (baseHeight - spriteMap.height)
    }
}