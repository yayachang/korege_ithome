import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import scene.*


suspend fun main() = Korge(Korge.Config(module = ConfigModule))

object ConfigModule : Module() {

	//視窗屬性
    override val bgcolor: RGBA = Colors["#2b2b2b"]
    override val size = SizeInt(960, 540) // Virtual Size
    override val windowSize = SizeInt(1280, 720) // Window Size

	//入口Scene設定
    override val mainScene = Splash::class
    override var title: String = "My First KorGE Game"
    override val clipBorders = false

	//asynchronous inject，當需要使用到時會幫忙自動產生所需要的場景object
    override suspend fun AsyncInjector.configure() {
        mapPrototype { Splash() }
        mapPrototype { Menu() }
        mapPrototype { GamePlay() }
        mapPrototype { GameOver() }
        mapPrototype { Rank() }
    }
}