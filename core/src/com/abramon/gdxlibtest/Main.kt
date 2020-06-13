package com.abramon.gdxlibtest

import com.abramon.gdxlibtest.screens.BollScreen
import com.abramon.gdxlibtest.screens.MainMenuScreen
import com.abramon.gdxlibtest.screens.TowerScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

class Main : Game()
{
    var bs: BollScreen? = null
    var ts: TowerScreen? = null
    var mms: MainMenuScreen? = null


    override fun create() {
        println("height = ${Gdx.app.graphics.height}   width = ${Gdx.app.graphics.width}")
        this.mms = MainMenuScreen(this)
        this.setScreen(this.mms!!)
        println("deltaTime = ${Gdx.app.graphics.deltaTime}")
    }

    override fun dispose() {
//        mms!!.dispose()
    }


}