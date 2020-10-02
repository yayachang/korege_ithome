package yaya.idv.myserver.service

import yaya.idv.myserver.model.UserScore
import yaya.idv.myserver.repository.UserScoreRepository

class UserScoreService {
    val userScoreRepository = UserScoreRepository()

    suspend fun saveScore(data: UserScore) {
        if(userScoreRepository.get(data.user) == null){
            userScoreRepository.add(data)
        }else{
            userScoreRepository.update(data)
        }
    }

    suspend fun getRankList():List<UserScore> {
        return userScoreRepository.get()
    }
}