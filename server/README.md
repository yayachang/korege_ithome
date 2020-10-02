# Server-Ktor專案
* Ktor安裝教學: https://ithelp.ithome.com.tw/articles/10247310
* MySQL Server安裝教學: https://ithelp.ithome.com.tw/articles/10247808

* 專案編譯執行: 
```
gradle run
```
* Server資料庫設定(DatabaseFactory.kt設定連線MySQL位置及帳號密碼)
```
config.driverClassName = "com.mysql.cj.jdbc.Driver"
config.jdbcUrl = "jdbc:mysql://localhost:3306/mygame"
config.username = "xxxx"
config.password = "xxxx"
```

