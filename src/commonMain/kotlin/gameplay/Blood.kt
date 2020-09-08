package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Blood : Container() {

    val count = 5
    val defaultX = ItemManager.BASE_WIDTH
    val defaultY = ItemManager.BASE_HEIGHT / 2
    var hearts = arrayListOf<Image>()
    lateinit var imageHeartEmpty: Bitmap
    lateinit var imageHeartFull: Bitmap

    val maxValue = 5
    val initValue = 5
    var nowValue = initValue

    suspend fun load() {
        imageHeartEmpty = resourcesVfs["hud_heartEmpty.png"].readBitmap()
        imageHeartFull = resourcesVfs["hud_heartFull.png"].readBitmap()
        for (i in 0 until count) {
            val heart = image(imageHeartFull)
            hearts.add(heart)
        }
    }
    fun initPosition() {
        hearts.forEachIndexed { index, image ->
            image.apply {
                if (index == 0) {
                    position(defaultX, defaultY)
                } else {
                    alignLeftToRightOf(hearts[index - 1])
                    alignTopToTopOf(hearts[index - 1])
                }
            }
        }
    }

    fun plus(){
        if(nowValue < maxValue){
            nowValue ++
        }
    }

    fun minus(){
        if(nowValue > 0) {
            nowValue--
        }
    }

    fun empty(){
        nowValue = 0
    }

    fun full(){
        nowValue = maxValue
    }

    fun update() {
        for (i in 0 until count) {
            if (i < nowValue) {
                hearts[i].bitmap = imageHeartFull.slice()
            } else {
                hearts[i].bitmap = imageHeartEmpty.slice()
            }
        }
    }
}