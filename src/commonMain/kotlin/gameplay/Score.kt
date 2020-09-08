package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.extract
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Score : Container() {
    val count = 5
    val BASE_WIDTH = 32
    val BASE_HEIGHT = 40

    val defaultX = ItemManager.BASE_WIDTH * 6
    val defaultY = ItemManager.BASE_HEIGHT / 2

    lateinit var scoreHead: Image
    lateinit var scoreBitmapSlice: Bitmap
    var scores = arrayListOf<Image>()
    val maxValue = 9999
    val initValue = 0
    var nowValue = initValue

    suspend fun load() {
        scoreHead = image(resourcesVfs["hud_coins.png"].readBitmap())
        scoreBitmapSlice = resourcesVfs["numbers.png"].readBitmap()
        for (i in 0 until count) {
            scores.add(image(loadNumber(0)))
        }
    }

    private fun loadNumber(value: Int): Bitmap {
        //https://github.com/korlibs/korge/issues/235 slice bitmap workaround
        return scoreBitmapSlice.extract(value * BASE_WIDTH, 0, BASE_WIDTH, BASE_HEIGHT)
    }

    fun initPosition() {
        scoreHead.position(defaultX, defaultY)

        scores.forEachIndexed { index, image ->
            image.apply {
                if (index == 0) {
                    centerOn(scoreHead)
                    alignLeftToRightOf(scoreHead)
                    x = x + BASE_WIDTH / 2
                } else {
                    alignLeftToRightOf(scores[index - 1])
                    alignTopToTopOf(scores[index - 1])
                }
            }
        }
    }

    fun plus(dt:Int){
        if(nowValue < maxValue){
            nowValue += dt
        }
    }

    fun update() {
        scores[0].bitmap = loadNumber((nowValue / 10000)).slice()
        scores[1].bitmap = loadNumber((nowValue % 10000 / 1000)).slice()
        scores[2].bitmap = loadNumber((nowValue % 1000) / 100).slice()
        scores[3].bitmap = loadNumber((nowValue % 100) / 10).slice()
        scores[4].bitmap = loadNumber((nowValue % 10)).slice()
    }
}