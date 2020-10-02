package gameplay

import com.soywiz.korge.view.*

enum class ITEM_TYPE {
    NONE,
    FLOOR,
    COIN,
    OBSTACLE,
    ENEMY
}

object ItemManager {
    val BASE_WIDTH = 70.0
    val BASE_HEIGHT = 70.0
    var items = ArrayList<Item>()

    var scoreItem = ArrayList<View>()
    var hurtItem = ArrayList<View>()

    var OFFSET = BASE_WIDTH * 2
    var BASE_FLOOR: Floor? = null


    fun init() {
        items.clear()
        scoreItem.clear()
        hurtItem.clear()

        val stageValue = listOf<Int>(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 2, 2, 4, 4, 2, 2, 2, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)

        var index = 0
        for (y in 0..5) {
            for (x in 0..37) {
                val itemType = ITEM_TYPE.values()[stageValue[index++]]
                val item = when (itemType) {
                    ITEM_TYPE.FLOOR -> {
                        Floor().run {
                            defaultX = x * BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT + OFFSET)
                        }
                    }
                    ITEM_TYPE.COIN -> {
                        Coin().run {
                            defaultX = x * BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT + OFFSET)
                        }
                    }

                    ITEM_TYPE.OBSTACLE -> {
                        Obstacle().run {
                            defaultX = x * BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT + OFFSET)
                        }
                    }

                    ITEM_TYPE.ENEMY -> {
                        Enemy().run {
                            defaultX = x * BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT + OFFSET)
                        }
                    }
                    ITEM_TYPE.NONE -> {
                        null
                    }

                }

                item?.also {
                    if (itemType == ITEM_TYPE.COIN) {
                        scoreItem.add(item.root)
                    }
                    if (itemType == ITEM_TYPE.OBSTACLE || itemType == ITEM_TYPE.ENEMY) {
                        hurtItem.add(item.root)
                    }
                    items.add(item)
                }


            }
        }


    }

    suspend fun load(parentView: Container) {
        items.forEach {
            parentView.addChild(it)
            it.load()
            it.setOffset(BASE_WIDTH, BASE_HEIGHT)
            if (it is Floor) {
                BASE_FLOOR = it
            }
        }
    }

    fun setCollision(alien: Alien, parentView: Container) {
        items.forEach {
            when (it) {
                is Coin -> {
                    it.addUpdater {
                        if (collidesWith(alien.sprite)) {
                            parentView.removeChild(this)
                        }
                    }
                }
                is Enemy -> {
                    it.addUpdater {
                        if (collidesWithShape(alien.sprite)) {
                            hurtItem.remove(this)
                        }
                    }
                }
                is Obstacle -> {

                    it.addUpdater {
                        if (collidesWithShape(alien.sprite)) {
                            hurtItem.remove(this)
                        }
                    }
                }
            }
        }
    }

    fun move() {
        items.forEach {
            it.move()
        }
    }

    fun stop() {
        items.forEach {
            it.stop()
        }
        items.clear()
        scoreItem.clear()
        hurtItem.clear()
    }

    fun nextPage() {

    }
}

