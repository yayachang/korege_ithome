package scene

import gameplay.Alien
import gameplay.CHARACTER

object SharedData {
    var alien = Alien()
    var SELECT_RUN_ALIEN:CHARACTER = CHARACTER.GREEN
    var GAME_SCORE:Int = 0
    var IS_GAME_OVER_SUCCES = true
}