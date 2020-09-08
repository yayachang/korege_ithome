package gameplay

import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Obstacle  : Item() {
    override suspend fun load() {
        image(resourcesVfs["rock.png"].readBitmap())
    }
}