package gameplay

import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Background : Item() {
    init {
        moveSpeed = 0
    }
    override suspend fun load() {
        image(resourcesVfs["bg_shroom.png"].readBitmap())
    }
}