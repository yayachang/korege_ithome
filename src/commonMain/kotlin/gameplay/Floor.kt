package gameplay

import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Floor : Item() {
    override suspend fun load() {
        image(resourcesVfs["grassMid.png"].readBitmap())
    }
}