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
    STAND,
    WALK,
    JUMP,
    FALL,
    HURT,
    DEAD,
    GOAL
}

enum class CHARACTER {
    GREEN,
    PURPLE,
    PINK,
    BEIGE,
    YELLOW
}

class Alien : Container() {

    var status = STATUS.STAND
    var character = CHARACTER.GREEN

    lateinit var walkAnimation: SpriteAnimation
    lateinit var hurtAnimation: SpriteAnimation
    lateinit var goalAnimation: SpriteAnimation

    lateinit var sprite: Sprite
    lateinit var walkBitmp: Bitmap
    lateinit var headBitmap: Bitmap
    lateinit var standBitmap: Bitmap
    lateinit var jumpBitmap: Bitmap
    lateinit var hurtBitmap: Bitmap
    lateinit var deadBitmap: Bitmap
    lateinit var goalBitmap: Bitmap

    var lastSTATUS = STATUS.WALK

    var defaultHeight = 0.0

    var alienWalkCount = 2
    var alienGoalCount = 2
    var alienWalkSpeed = 0.3
    var alienGoalSpeed = 0.3


    suspend fun load(character:CHARACTER) {

        this.character = character

        when(character){
            CHARACTER.GREEN->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                headBitmap = resourcesVfs["green_alien_head.png"].readBitmap()
                standBitmap = resourcesVfs["green_alien_stand.png"].readBitmap()
                walkBitmp = resourcesVfs["green_alien_walk.png"].readBitmap()
                hurtBitmap = resourcesVfs["green_alien_hurt.png"].readBitmap()
                jumpBitmap = resourcesVfs["green_alien_jump.png"].readBitmap()
                deadBitmap = hurtBitmap
                goalBitmap = resourcesVfs["green_alien_goal.png"].readBitmap()
            }
            CHARACTER.PURPLE->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                headBitmap = resourcesVfs["purple_alien_head.png"].readBitmap()
                standBitmap = resourcesVfs["purple_alien_stand.png"].readBitmap()
                walkBitmp = resourcesVfs["purple_alien_walk.png"].readBitmap()
                hurtBitmap = resourcesVfs["purple_alien_hurt.png"].readBitmap()
                jumpBitmap = resourcesVfs["purple_alien_jump.png"].readBitmap()
                deadBitmap = hurtBitmap
                goalBitmap = resourcesVfs["purple_alien_goal.png"].readBitmap()

            }
            CHARACTER.PINK->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                headBitmap = resourcesVfs["pink_alien_head.png"].readBitmap()
                standBitmap = resourcesVfs["pink_alien_stand.png"].readBitmap()
                walkBitmp = resourcesVfs["pink_alien_walk.png"].readBitmap()
                hurtBitmap = resourcesVfs["pink_alien_hurt.png"].readBitmap()
                jumpBitmap = resourcesVfs["pink_alien_jump.png"].readBitmap()
                deadBitmap = hurtBitmap
                goalBitmap = resourcesVfs["pink_alien_goal.png"].readBitmap()

            }
            CHARACTER.BEIGE->{
                alienWalkCount = 2
                alienWalkSpeed = 0.3
                headBitmap = resourcesVfs["beige_alien_head.png"].readBitmap()
                standBitmap = resourcesVfs["beige_alien_stand.png"].readBitmap()
                walkBitmp = resourcesVfs["beige_alien_walk.png"].readBitmap()
                hurtBitmap = resourcesVfs["beige_alien_hurt.png"].readBitmap()
                jumpBitmap = resourcesVfs["beige_alien_jump.png"].readBitmap()
                deadBitmap = hurtBitmap
                goalBitmap = resourcesVfs["beige_alien_goal.png"].readBitmap()

            }
            CHARACTER.YELLOW->{
                alienWalkCount = 2
                alienWalkSpeed = 0.3
                headBitmap = resourcesVfs["yellow_alien_head.png"].readBitmap()
                standBitmap = resourcesVfs["yellow_alien_stand.png"].readBitmap()
                walkBitmp = resourcesVfs["yellow_alien_walk.png"].readBitmap()
                hurtBitmap = resourcesVfs["yellow_alien_hurt.png"].readBitmap()
                jumpBitmap = resourcesVfs["yellow_alien_jump.png"].readBitmap()
                deadBitmap = hurtBitmap
                goalBitmap = resourcesVfs["yellow_alien_goal.png"].readBitmap()

            }
        }

        hurtAnimation = SpriteAnimation(spriteMap = hurtBitmap, spriteWidth = hurtBitmap.width, spriteHeight = hurtBitmap.height)
        walkAnimation = SpriteAnimation(
                spriteMap = walkBitmp,
                spriteWidth =  walkBitmp.width/alienWalkCount,
                spriteHeight = walkBitmp.height,
                marginTop = 0,
                marginLeft = 0,
                columns = alienWalkCount,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0
        )
        goalAnimation  = SpriteAnimation(
                spriteMap = goalBitmap,
                spriteWidth =  goalBitmap.width/alienGoalCount,
                spriteHeight = goalBitmap.height,
                marginTop = 0,
                marginLeft = 0,
                columns = alienGoalCount,
                rows = 1,
                offsetBetweenColumns = 0,
                offsetBetweenRows = 0
        )
        sprite = sprite(standBitmap)
    }

    fun dead(){
        if(status != STATUS.GOAL && status!=STATUS.DEAD) {
            changeStatus()
            sprite = sprite(deadBitmap)
            status = STATUS.DEAD
        }

    }
    fun goal(){
        if(status != STATUS.GOAL && status!=STATUS.DEAD) {
            changeStatus()
            sprite = sprite(goalAnimation) {
                spriteDisplayTime = alienGoalSpeed.seconds
            }.apply {
                playAnimationLooped()
            }
            status = STATUS.GOAL
        }
    }

    fun hurt(): Boolean {
        if (status == STATUS.HURT) {
            return false
        } else {
            changeStatus()
            sprite = sprite(hurtAnimation) {
                spriteDisplayTime = 0.3.seconds
            }.apply {
                playAnimation(1)
                onAnimationCompleted {
                    status = lastSTATUS
                    if (status == STATUS.WALK) {
                        walk()
                    }
                }
            }

            lastSTATUS = status
            status = STATUS.HURT
            return true
        }
    }

    fun stop(){
        changeStatus()
        status = STATUS.STAND
        sprite = sprite(standBitmap)
    }

    fun walk() {
        changeStatus()
        sprite = sprite(walkAnimation) {
            spriteDisplayTime = alienWalkSpeed.seconds
        }.apply {
            playAnimationLooped()
        }
        status = STATUS.WALK
    }

    fun changeStatus() {
        if (sprite != null && sprite.parent != null) {
            sprite.stopAnimation()
            sprite.removeFromParent()
        }
    }

    fun jump() {
        if (status != STATUS.JUMP && status != STATUS.FALL &&  status != STATUS.GOAL && status!=STATUS.DEAD)  {
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
            STATUS.HURT -> {
                if (sprite.alpha > 0) {
                    sprite.alpha -= 0.1
                } else if (sprite.alpha <= 0.5) {
                    sprite.alpha = 1.0
                }
            }
            STATUS.JUMP -> {
                y = y - 4
                if (y <= defaultHeight - 100) {
                    fall()
                }
            }
            STATUS.FALL -> {
                y = y + 6
                if (y >= defaultHeight) {
                    walk()
                }
            }
            STATUS.DEAD,STATUS.GOAL -> {
                if (y < defaultHeight) {
                    y = y + 6
                }
            }
        }
    }
}
