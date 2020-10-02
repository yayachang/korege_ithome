package yaya.idv.myserver.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import yaya.idv.myserver.model.UserScore
import yaya.idv.myserver.service.UserScoreService


fun Route.userScore() {
    post("/uploadScore") {
        var data = Gson().fromJson(call.receive<String>(), UserScore::class.java)
        if (data == null) {
            call.respond(HttpStatusCode.BadRequest, "玩家資料不存在")
        } else {
            UserScoreService().saveScore(data)
            call.respond(HttpStatusCode.OK)
        }
    }

    get("/getRankList") {
        call.respond(HttpStatusCode.OK, UserScoreService().getRankList())
        val resultList = Gson().fromJson<List<UserScore>>(UserScoreService().getRankList().toString(), object : TypeToken<List<UserScore?>?>() {}.type)

    }
}

