package yaya.idv.myserver

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import yaya.idv.myserver.api.userScore
import yaya.idv.myserver.database.DatabaseFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson()
    }
    DatabaseFactory.init()
    DatabaseFactory.createTable()

    routing {
        userScore()
    }
}

