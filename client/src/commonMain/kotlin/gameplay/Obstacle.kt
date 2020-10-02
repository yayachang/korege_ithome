package gameplay

import com.soywiz.korge.view.hitShape
import com.soywiz.korge.view.image
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.vector.rect

class Obstacle : Item() {

    val offsetX = 25.0
    val offsetY = 20.0

    override suspend fun load() {
        val bitamp = resourcesVfs["rock.png"].readBitmap()
        image(bitamp)
        //solidRect(bitamp.width - offsetX, (bitamp.height + offsetY) / 2, Colors.BLUE).xy(offsetX / 2, (bitamp.height + offsetY) / 2)
        hitShape {
            rect(offsetX / 2, (bitamp.height + offsetY) / 2, bitamp.width - offsetX, (bitamp.height + offsetY) / 2)
        }
    }


    override suspend fun setOffset(baseWidth: Double, baseHeight: Double) {

    }


}