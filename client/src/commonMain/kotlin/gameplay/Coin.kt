package gameplay

import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Coin : Item() {
    override suspend fun load() {
        image(resourcesVfs["hud_coins.png"].readBitmap())
    }

    override suspend fun setOffset(baseWidth: Double, baseHeight: Double) {

    }
}