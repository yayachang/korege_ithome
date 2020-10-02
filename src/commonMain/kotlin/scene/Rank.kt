package scene

import ConfigModule
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.TtfFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.VfsOpenMode
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openSync
import gameplay.Score
import model.UserScore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Rank : Scene() {

    lateinit var ttfFont: TtfFont
    lateinit var rankImage: Image
    lateinit var myScoreImage: Image
    lateinit var image: Image

    override suspend fun Container.sceneInit() {

        ttfFont = TtfFont(resourcesVfs["NotoSans-Black.ttf"].readAll().openSync())

        //背景圖
        image = image(resourcesVfs["bg_shroom.png"].readBitmap()) {
            anchor(0.5, 0.5)
            scale(ConfigModule.size.width / width, ConfigModule.size.height / height)
            position(scaledWidth / 2, scaledHeight / 2)
        }
        val bg = this

        //排行榜
        val textString =  "RANK"
        val fontSize = 40.0
        val rankBitmap = NativeImage(width = bg.width.toInt(), height = 100).apply {
            getContext2d().fillText(textString,
                    x = 0,
                    y = fontSize,
                    font = ttfFont,
                    fontSize = fontSize.toDouble(),
                    color = Colors.BLUE)
        }

        rankImage = image(rankBitmap) {
            position((image.scaledWidth) / 2 - (textString.count() * fontSize) / 4, 20.0)
        }

        val myScoreString =  "My Highest Score:"
        val myScorefontBitmap = NativeImage(width = width.toInt(), height = 100).apply {
            getContext2d().fillText(myScoreString,
                    x = 0,
                    y = fontSize,
                    font = ttfFont,
                    fontSize = fontSize.toDouble(),
                    color = Colors.BLACK)
        }


        //加入分數
        val myHighestScore = Score().apply { load() }
        addChild(myHighestScore)
        myHighestScore.initPosition()
        myHighestScore.nowValue = getHighestScore()
        myHighestScore.update()

        myScoreImage = image(myScorefontBitmap) {
            position((image.scaledWidth) / 2 - ((myScoreString.count() * fontSize) / 4 + myHighestScore.width/2), 0.0)
        }

        myScoreImage.alignTopToBottomOf(rankImage)
        myScoreImage.y  -= 20.0
        myHighestScore.alignTopToTopOf(myScoreImage)
        myHighestScore.x = 180.0
        myHighestScore.y -= (myHighestScore.scaledHeight/2  + 10.0)

        launch {
            api.UserScore.upload(UserScore(user = "yaya", score = getHighestScore()))
            val jsonResult = api.UserScore.getRankList()
            val resultList = Gson().fromJson<List<UserScore>>(jsonResult, object : TypeToken<List<UserScore>>() {}.type)

            var upperObject = myScoreImage
            var index = 1
            for(data in resultList){
                val nameString = "$index: ${data.user}"
                val nameBitamp = NativeImage(width = width.toInt(), height = 100).apply {
                    getContext2d().fillText(nameString,
                            x = 0,
                            y = fontSize,
                            font = ttfFont,
                            fontSize = fontSize.toDouble(),
                            color = Colors.BLACK)
                }
                val rankResultImage = image(nameBitamp) {
                    position((image.scaledWidth) / 2 - ((myScoreString.count() * fontSize) / 4 + myHighestScore.width/2), 20)
                }


                rankResultImage.alignTopToBottomOf(upperObject)
                upperObject = rankResultImage

                //加入分數
                val score = Score().apply { load() }
                addChild(score)
                score.initPosition()
                score.nowValue = data.score
                score.update()

                score.alignTopToTopOf(rankResultImage)
                score.y -= (score.scaledHeight/2  + 10.0)

                index ++

            }
            //加上下一步按鈕
            image( resourcesVfs["next.png"].readBitmap()) {
                anchor(0.5, 0.5)
                position(image.width - this.width + 10 , image.height-this.height/2)
                onClick {
                    launchImmediately {
                        sceneContainer.changeTo<Menu>()
                    }
                }
            }

        }
    }

    override suspend fun Container.sceneMain() {

        keys {
            down(Key.SPACE) {
                println("Key down")
            }
            up(Key.SPACE) {
                println("Key up")
            }
        }

    }


    suspend fun getHighestScore():Int{
        val scoreFile = resourcesVfs["score.txt"]
        if(!scoreFile.exists()){
            scoreFile.open(VfsOpenMode.CREATE_NEW)
        }
        val savedScoreString = scoreFile.readString()
        var savedScore = 0
        if(savedScoreString.isNotEmpty()){
            savedScore = savedScoreString.toInt()
        }
        println("savedScore = $savedScore")
        if(SharedData.GAME_SCORE > savedScore){
            savedScore = SharedData.GAME_SCORE
            scoreFile.writeString(SharedData.GAME_SCORE.toString())
            println("new savedScore = $savedScore")
        }
        return savedScore
    }

}