package com.abramon.gdxlibtest.screens

import com.abramon.gdxlibtest.Main
import com.abramon.gdxlibtest.controls.TowerScreenEvent
import com.abramon.gdxlibtest.model.Gun
import com.abramon.gdxlibtest.model.Tank
import com.badlogic.gdx.Application
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import java.text.Format


class TowerScreen(mn: Main/*Это и есть класс Game*/) : Screen {

    val main: Main = mn
    var sw: Float = Gdx.app.graphics.width.toFloat()
    var sh: Float = Gdx.app.graphics.height.toFloat()
    var hwRatio: Float = 1f
    private val towerScreenEvent: TowerScreenEvent = TowerScreenEvent(main)
    private var allowRender: Boolean = false
    //val sprite: Sprite = Sprite(Texture("tank.png"))
    //val batch: Batch = SpriteBatch()

    init {
        Gdx.input.inputProcessor = towerScreenEvent
       // sprite.x = 0f
       // sprite.y = 0f
    }

    override fun show() {
        allowRender = true
        //towerScreenEvent.addActor(tank)
        //tank.name = "tank1"
        //tank.setSpritePos(sw/2f, sh/2f)
        println("TowerScreen show")
    }

//    fun tankOnAimGun(): Boolean {
//        if (gun.isVisible) {
//            if (gun.circleAim.contains(tank.x, tank.y))  //(Intersector.overlaps(gun.circleAim, tank.getRect())) {
//                return true
//            }
//
//        return false
//    }
//
//    fun tankOnFireGun(): Boolean {
//        if (gun.isVisible) {
//            if (gun.circleFire.contains(tank.x, tank.y))  //(Intersector.overlaps(gun.circleAim, tank.getRect())) {
//                return true
//        }
//
//        return false
//    }

    override fun render(delta: Float) {
        if (!allowRender) return
        if (Gdx.app.type == ApplicationType.Android){
            Gdx.gl.glClearColor(0.200f, 0.201f, 0.200f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        } else {
            Gdx.gl30.glClearColor(0.200f, 0.201f, 0.200f, 1f)
            Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT)
        }

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

//        if (tankOnAimGun()){
//            println("tank on aim")
//        }
//        if (tankOnFireGun()){
//            println("tank on fire")
//        }

//        batch.begin()
//        sprite.draw(batch)
//        batch.end()

        towerScreenEvent.act(delta)
        towerScreenEvent.draw()
        //Gdx.gl.glDisable(GL30.GL_BLEND)
    }

    override fun resize(width: Int, height: Int) {
        sw = width.toFloat()
        sh = height.toFloat()
        hwRatio = sh/sw
        towerScreenEvent.viewport.setWorldSize(sw, sh)
        towerScreenEvent.viewport.update(width, height, true)
        println("TowerScreen resize")
    }

    override fun dispose() {
        allowRender = false
        towerScreenEvent.dispose()
        println("TowerScreen dispose")
    }

    override fun pause() {
        allowRender = false
        println("TowerScreen pause")
    }

    override fun resume() {
        allowRender = true
        println("TowerScreen resume")
    }

    override fun hide() {
        allowRender = false
        println("TowerScreen hide")
    }


}