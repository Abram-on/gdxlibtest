package com.abramon.gdxlibtest.controls

import com.abramon.gdxlibtest.screens.BollScreen
import com.abramon.gdxlibtest.screens.MainMenuScreen
import com.abramon.gdxlibtest.screens.TowerScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.audio.Sound

enum class MenuTypeSet{
    NONE,
    BOLL,
    TEST2,
    EXIT
}

class MainMenuEvent(mms: MainMenuScreen): InputProcessor {

    var mainMenuScreen: MainMenuScreen? = null
    var wavSound: Sound = Gdx.audio.newSound(Gdx.files.internal("photo.wav"))

    init {
        this.mainMenuScreen = mms
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        mainMenuScreen!!.mouseMoved(screenX, screenY)
        if (mainMenuScreen!!.menu1.getRect().contains(screenX.toFloat(), screenY.toFloat())){
            mainMenuScreen!!.menuTypeSet = MenuTypeSet.BOLL
            wavSound.play(0.5f)

            return true
        }
        if (mainMenuScreen!!.menu2.getRect().contains(screenX.toFloat(), screenY.toFloat())){
            mainMenuScreen!!.menuTypeSet = MenuTypeSet.TEST2
            return true
        }
        if (mainMenuScreen!!.menuExit.getRect().contains(screenX.toFloat(), screenY.toFloat())){
            mainMenuScreen!!.menuTypeSet = MenuTypeSet.EXIT
            return true
        }
        mainMenuScreen!!.menuTypeSet = MenuTypeSet.NONE

        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        println(Input.Keys.toString(keycode))

        when (keycode) {
            Input.Keys.ESCAPE -> {
                println("Escape")
                Gdx.app.exit()
                return true
            }
            Input.Keys.ENTER -> {
                println("Enter 222")
                Gdx.app.exit()
                return true
            }
        }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        mainMenuScreen!!.mouseMoved(screenX, screenY)
        if (button == Input.Buttons.LEFT){
            if (mainMenuScreen!!.menu1.getRect().contains(screenX.toFloat(), screenY.toFloat())){
                mainMenuScreen!!.main.bs = BollScreen(mainMenuScreen!!.main)
                mainMenuScreen!!.main.screen = mainMenuScreen!!.main.bs
                return true
            }
            if (mainMenuScreen!!.menu2.getRect().contains(screenX.toFloat(), screenY.toFloat())){
                mainMenuScreen!!.main.ts = TowerScreen(mainMenuScreen!!.main)
                mainMenuScreen!!.main.screen = mainMenuScreen!!.main.ts
                return true
            }
            if (mainMenuScreen!!.menuExit.getRect().contains(screenX.toFloat(), screenY.toFloat())){
                mainMenuScreen!!.dispose()
                Gdx.app.exit()
                return true
            }
        }
        return false
    }

}