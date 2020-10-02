package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.extract
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class GameTimer : Container() {

    val count = 2
    val BASE_WIDTH = 32
    val BASE_HEIGHT = 40
    val defaultX = ItemManager.BASE_WIDTH * 10
    val defaultY = ItemManager.BASE_HEIGHT / 2

    lateinit var timerHead: Image
    lateinit var timerBitmapSlice: Bitmap
    var timers = arrayListOf<Image>()
    val initTime = 10
    var totalTime = initTime
    var isStop = false

    suspend fun load() {
        timerHead = image(resourcesVfs["clock.png"].readBitmap())
        timerBitmapSlice = resourcesVfs["numbers.png"].readBitmap()
        timers.add(image(loadScore(initTime/10)))
        timers.add(image(loadScore(0)))
    }

    private fun loadScore(value: Int): Bitmap {
        //https://github.com/korlibs/korge/issues/235 slice bitmap workaround
        return timerBitmapSlice.extract(value * BASE_WIDTH, 0, BASE_WIDTH, BASE_HEIGHT)
    }

    fun initPosition() {
        timerHead.position(defaultX, defaultY)

        timers.forEachIndexed { index, image ->
            image.apply {
                if (index == 0) {
                    centerOn(timerHead)
                    alignLeftToRightOf(timerHead)
                    x = x + BASE_WIDTH / 2
                } else {
                    alignLeftToRightOf(timers[index - 1])
                    alignTopToTopOf(timers[index - 1])
                }
            }
        }
    }

    fun minus(){
        if(totalTime != 0 && !isStop){
            totalTime -=1
        }
    }

    fun start(){
        isStop = false
    }

    fun stop(){
        isStop = true
    }

    fun update() {
        timers[0].bitmap = loadScore((totalTime / 10)).slice()
        timers[1].bitmap = loadScore((totalTime % 10)).slice()
    }
}