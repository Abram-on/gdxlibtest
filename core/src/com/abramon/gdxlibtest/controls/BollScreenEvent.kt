package com.abramon.gdxlibtest.controls

import com.abramon.gdxlibtest.Main
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor

class BollScreenEvent(mn: Main): InputProcessor {

    var main: Main = mn
    var moveUp: Boolean = false
    var moveDown: Boolean = false
    var moveLeft: Boolean = false
    var moveRight: Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        main.bs!!.mouseMoved(screenX, screenY)
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.ESCAPE -> {
                println("Escape")
                main.screen = main.mms
                return true
            }
            Input.Keys.ENTER -> {
                println("Enter111")
                return true
            }
            Input.Keys.UP -> {
                println("Up")
                moveUp = true
                return true
            }
            Input.Keys.DOWN -> {
                println("Down")
                moveDown = true
                return true
            }
            Input.Keys.LEFT -> {
                println("Left")
                main.bs!!.g_jump_X = - main.bs!!.g_jump
                return true
            }
            Input.Keys.RIGHT -> {
                println("Right")
                main.bs!!.g_jump_X = main.bs!!.g_jump
                return true
            }
            Input.Keys.SPACE -> {
                println("Space")
                main.bs!!.g_jump_Y = main.bs!!.g_jump
                return true
            }
            Input.Keys.PLUS -> {
                println("Plus")
                main.bs!!.g_jump = main.bs!!.g_jump + 5000
                return true
            }
            Input.Keys.MINUS -> {
                println("Plus")
                main.bs!!.g_jump = main.bs!!.g_jump - 5000
                return true
            }
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.UP -> {
                println("Up")
                moveUp = false
                return false
            }
            Input.Keys.DOWN -> {
                println("Down")
                moveDown = false
                return false
            }

            Input.Keys.LEFT -> {
                println("Left")
                moveLeft = false
                return false
            }
            Input.Keys.RIGHT -> {
                println("Right")
                moveRight = false
                return false
            }
        }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        main.bs!!.mouseMoved(screenX, screenY)
        return true
    }

}
