package com.abramon.gdxlibtest.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import java.awt.KeyboardFocusManager
import java.awt.KeyboardFocusManager.setCurrentKeyboardFocusManager
import kotlin.math.cos
import kotlin.math.sin
import com.badlogic.gdx.scenes.scene2d.Touchable.enabled


class Tank(texture: Texture, nameIn: String): Actor() {

    private val step: Float = 10f
    private val deg: Float =  6f
    private val degToRad: Float = 0.0174533f
    //private val keyboardFocusManager: KeyboardFocusManager? = null
    val listener: InputListener = InputListener()

    var sprite: Sprite = Sprite(texture)
    var forwardMove: Boolean = false
    var backwardMove: Boolean = false
    var leftMove: Boolean = false
    var rightMove: Boolean = false
    var event: InputEvent = InputEvent()



    init {
        this.isVisible = false
        this.rotation = 0f
        this.name = nameIn
        touchable = enabled
        //listener.touchDown(event, x, y, event.pointer, event.button)
//        addListener(object : InputListener() {
//
//            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
//                println("Tank name=$name touchUp")
//                super.touchUp(event, x, y, pointer, button)
//            }
//
//            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//                println("Tank name=$name touchDown")
//                return true//super.touchDown(event, x, y, pointer, button)
//            }
//        })
        //this.listeners.add(listener)

        //listeners.begin()

        //listeners.end()
        //Actions.addListener()

    }


    fun set(xIn: Float, yIn: Float){
        val v2: Vector2 = stage.screenToStageCoordinates(Vector2(xIn, yIn))
        width = sprite.regionWidth.toFloat()
        height = sprite.regionHeight.toFloat()
        setOrigin(Align.center)
        x = v2.x - originX
        y = v2.y - originY
        println("Tank set $xIn,  $yIn,  --->  ${x},  ${y} ")
        setBounds(x, y, width, height)
        isVisible = true
        touchable = enabled

        println("Tank x=$x,  y=$y,  originX=$originX,  originY=$originY")
        this.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val v2 = stage.screenToStageCoordinates(Vector2(x, y))
                //x = v2.x
                println("Tank name=$name touchUp")
                //super.touchUp(event, v2.x, v2.y, pointer, button)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val v2 = stage.screenToStageCoordinates(Vector2(x, y))
                //x = v2.x
                println("Tank name=$name touchDown")
                return true
                //return super.touchDown(event, v2.x, v2.y, pointer, button)
            }

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                println("Tank name=$name enter")
                //super.enter(event, x, y, pointer, fromActor)
            }
        })
    }

    fun getRect(): Rectangle {
        return sprite.boundingRectangle
    }

//    fun stForwardMove(flag: Boolean){
//        forwardMove = flag
//    }


    private fun updateMotion() {
        val stepX: Float = step * cos(toRad(rotation))
        val stepY: Float = step * sin(toRad(rotation))

        if (forwardMove) {
            x +=  stepX
            y += stepY
            addAction(Actions.moveTo(x, y,0.5f, Interpolation.linear))
        }

        if (backwardMove) {
            x -=  stepX
            y -= stepY
            addAction(Actions.moveTo(x, y,0.5f, Interpolation.linear))
        }

        if (leftMove) {
            addAction(Actions.rotateBy(deg, 0.5f, Interpolation.linear))
        }

        if (rightMove) {
            addAction(Actions.rotateBy(-deg,0.5f, Interpolation.linear))
        }
    }

    private fun toRad(deg: Float): Float{
        return deg * degToRad
    }

//    fun setSpritePos(xIn: Float, yIn: Float){
//        x = xIn
//        y = yIn
//        setPosition(x, y)
//        setBounds(x, y, width, height);
//    }

    override fun fire(event: Event?): Boolean {
        //if (event = null) this.listeners.items[1].handle(event)
        if (event != null) {
            println("GroupGuns name=$name fire")
            if (event.listenerActor != null)
                println("\tListenerActor=${event.listenerActor.name}")
            if (event.target != null)
                println("\tTarget=${event.target.name}")
        }
        event!!.bubbles = true
        return false
    }


    override fun act(delta: Float) {
        event
        super.act(delta)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch!!.draw(sprite, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        updateMotion()
    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor {
        println("$name hit x=$x, y=$y, touchable=$touchable")
        //println("Tank $name hit x=$x, y=$y")
        return this
//        return super.hit(x, y, touchable)

    }


}