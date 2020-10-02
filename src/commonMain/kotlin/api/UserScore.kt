package api

import com.soywiz.korio.dynamic.mapper.Mapper
import com.soywiz.korio.dynamic.serialization.stringifyTyped
import com.soywiz.korio.net.http.Http
import com.soywiz.korio.net.http.HttpClient
import com.soywiz.korio.serialization.json.Json
import com.soywiz.korio.stream.openAsync
import model.UserScore

object UserScore {

    suspend fun upload(data: UserScore){
        val content = Json.stringifyTyped(data, Mapper)
        val result = HttpClient().requestAsString(method = Http.Method.POST, url = "http://0.0.0.0:8080/uploadScore", content = content.openAsync())
        if(result.success){
            println("更新分數成功")
        }
    }
    suspend fun getRankList():String{
        val result = HttpClient().requestAsString(method =Http.Method.GET, url = "http://0.0.0.0:8080/getRankList")
        if(result.success){
            println("取得排行榜成功")
           return result.content
        }else{
            return "";
        }
    }
}