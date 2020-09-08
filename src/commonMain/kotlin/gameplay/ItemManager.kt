package gameplay

import com.soywiz.klock.milliseconds
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
    var items = ArrayList<Item?>()

    var OFFSET = BASE_WIDTH*2
    var BASE_FLOOR:Floor?=null

    fun init() {
        val stageValue = listOf<Int>(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 2, 2, 2, 3, 2, 2, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)

        var index = 0
        for (y in 0 .. 5) {
            for (x in 0..27) {

                val item = when (ITEM_TYPE.values()[stageValue[index++]]) {
                    ITEM_TYPE.FLOOR -> {
                        println("add floor")
                        Floor().run {
                            defaultX = x*BASE_WIDTH
                            position( defaultX, y * BASE_HEIGHT + OFFSET)
                        }
                    }
                    ITEM_TYPE.COIN -> {
                        println("add coin")
                        Coin().run {
                            defaultX = x*BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT+ OFFSET)
                        }
                    }

                    ITEM_TYPE.OBSTACLE -> {
                        println("add obstacle")
                        Obstacle().run {
                            defaultX = x*BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT+ OFFSET)
                        }
                    }

                    ITEM_TYPE.ENEMY -> {
                        println("add enemy")
                        Enemy().run {
                            defaultX = x*BASE_WIDTH
                            position(defaultX, y * BASE_HEIGHT+ OFFSET)
                        }
                    }
                    else -> {
                        null
                    }
                }

                items.add(item)
            }
        }


    }

    suspend fun load(parentView: Container) {
        items.forEach {
            it?.also {
                parentView.addChild(it)
                it.load()
                println("item y:${it.y}")
                if(it is Floor){
                    BASE_FLOOR = it
                }
            }
        }
    }

    fun move() {
        items.forEach {
            it?.also {
                it.move()
            }
        }
    }

    fun nextPage(){

    }
}

