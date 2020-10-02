package yaya.idv.myserver.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import yaya.idv.myserver.entity.UserScores


object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
    }

    fun createTable(){
        transaction {
            SchemaUtils.create(UserScores)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://localhost:3306/mygame"
        config.username = "xxxx"
        config.password = "xxxx"

        config.validate()
        return HikariDataSource(config)
    }

}