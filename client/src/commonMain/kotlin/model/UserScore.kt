package model

import com.soywiz.korio.dynamic.mapper.Mapper

data class UserScore(var id: Long? = null, var user: String, var score: Int) {
    companion object{
        init {

            Mapper.registerType(UserScore::class) {
                UserScore(it["id"].gen(), it["user"].gen(), it["score"].gen())
            }
            Mapper.registerUntype(UserScore::class) {
                mapOf(
                        "id" to it.id,
                        "user" to it.user,
                        "score" to it.score
                )
            }


        }
    }
}