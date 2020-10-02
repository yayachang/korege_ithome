# Clinet-KorGE專案
* KorGE安裝教學: https://ithelp.ithome.com.tw/articles/10233525

* 專案編譯執行：
```
gradle runJvm
```
* 更改Server API 呼叫位置(src/commonMain/kotlin/api/UserScore.kt
)：
```
HttpClient().requestAsString(method = Http.Method.POST, url = "http://0.0.0.0:8080/uploadScore", content = content.openAsync())
HttpClient().requestAsString(method = Http.Method.GET, url = "http://0.0.0.0:8080/getRankList")

```
