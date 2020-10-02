package yaya.idv.myserver.repository

import io.ktor.html.insert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import yaya.idv.myserver.entity.UserScores
import yaya.idv.myserver.model.UserScore

class UserScoreRepository {



    suspend fun get(user:String): UserScore? {
        return transaction {
            UserScores.select({ UserScores.user eq user }).mapNotNull{
                toUserScore(it)
            }.singleOrNull()
        }
    }

    suspend fun add(data: UserScore) {
        transaction {
            UserScores.insert {
                it[user] = data.user
                it[score] = data.score
                it[updateTime] = System.currentTimeMillis()
            }
        }
    }

    suspend fun update(data: UserScore) {
        transaction {
            UserScores.update({ (UserScores.user eq data.user) and (UserScores.score less data.score) }) {
                it[score] = data.score
                it[updateTime] = System.currentTimeMillis()
            }
        }
    }




    suspend fun get(): List<UserScore> {
        val data = withContext(Dispatchers.IO) {
            transaction {
                UserScores.selectAll().orderBy(UserScores.score, SortOrder.DESC).mapNotNull {
                    toUserScore(it)
                }.toMutableList()
            }
        }
        return data
    }

    private fun toUserScore(row: ResultRow): UserScore =
        UserScore(
            id = row[UserScores.id].value,
            user = row[UserScores.user],
            score = row[UserScores.score]
        )

}