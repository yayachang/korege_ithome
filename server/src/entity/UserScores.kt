package yaya.idv.myserver.entity

object UserScores : org.jetbrains.exposed.dao.id.LongIdTable() {
    var user = varchar("user", 255).uniqueIndex()
    var score =  integer("score").default(0)
    var updateTime = long("updateTime")
}