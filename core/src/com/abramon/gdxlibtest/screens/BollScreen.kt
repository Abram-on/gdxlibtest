package com.abramon.gdxlibtest.screens

import com.abramon.gdxlibtest.Main
import com.abramon.gdxlibtest.controls.BollScreenEvent
import com.abramon.gdxlibtest.model.Rect
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import java.lang.String.format
import kotlin.math.sign


class BollScreen(mn: Main) : Screen {

    var sw: Float = Gdx.app.graphics.width.toFloat()
    var sh: Float = Gdx.app.graphics.height.toFloat()
    var hwRatio: Float = 1f

    var main: Main = mn
    var camera: OrthographicCamera = OrthographicCamera()
    var viewport : Viewport = ExtendViewport(sw, sh, camera)
    var batch: SpriteBatch = SpriteBatch()
    var font: BitmapFont = BitmapFont()

    var imgPosX: Float = 0f
    var imgPosY: Float = -sw/2f
    var boll: Rect = Rect(Texture("boll.png"), imgPosX, imgPosY)

    val g: Float = -980f
    val b: Float = -400f
    val bf: Float = -10f
    var sign: Float = 0f
    var g_jump: Float = 80000f
    var g_jump_Y: Float = 0f
    var g_jump_X: Float = 0f
    var speedY: Float = 0f
    var speedX: Float = 0f
    var dy: Float = 600f
    var min_pos_y: Float = -sh/2f
    var min_pos_x: Float = -sw/2f //+ boll.sprite.width
    var max_pos_x: Float = sw/2f - boll.sprite.width


    var v3 : Vector3 = Vector3()
    var mouse_x: Float = 0f
    var mouse_y: Float = 0f

    init {
        this.font.data.scaleX = 2f
        this.font.data.scaleY = 2f
        //this.camera = OrthographicCamera()
        //this.viewport = ExtendViewport(sw, sh, camera)
    }

    private val eventproc: BollScreenEvent = BollScreenEvent(mn)

    fun mouseMoved(x:  Int, y: Int){
        v3.x = x.toFloat()
        v3.y = y.toFloat()
        v3 = camera.unproject(v3)
        mouse_x = v3.x
        mouse_y = v3.y
    }

    private fun cameraZoom(){
            //через камеру
            if (imgPosY > (sh / 2f - boll.sprite.height - 1)) {
                camera.viewportHeight = (imgPosY + boll.sprite.height) * 2f
                camera.viewportWidth = (imgPosY + boll.sprite.height) * 2f / hwRatio
            } else {
                camera.viewportHeight = sh
                camera.viewportWidth = sw
            }
    }


    private fun mousePos(){
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin() 
            if (mouse_x < 0) font.color = Color.RED
            else font.color = Color.GREEN
            font.draw(batch, "x: ${format("%.0f",mouse_x)}", -sw/2f, sh/2f)

            if (mouse_y < 0) font.color = Color.RED
            else font.color = Color.GREEN
            font.draw(batch, "y: ${format("%.0f",mouse_y)}", -sw/2f, sh/2f - 60f)

            if (imgPosX < 0) font.color = Color.RED
            else font.color = Color.GREEN
            font.draw(batch, "x: ${format("%.0f",imgPosX)}", -sw/2f, sh/2f - 120f)

            if (imgPosY < 0) font.color = Color.RED
            else font.color = Color.GREEN
            font.draw(batch, "y: ${format("%.0f",imgPosY)}", -sw/2f, sh/2f - 210f)

            font.draw(batch, "jump: ${format("%.0f",g_jump)}", -sw/2f, sh/2f - 270f)
        batch.end()

    }

    override fun render(delta: Float) {
        if (Gdx.app.type == Application.ApplicationType.Android){
            Gdx.gl.glClearColor(0.854f, 0.945f, 0.776f, 0f)
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        } else {
            Gdx.gl30.glClearColor(0.854f, 0.945f, 0.776f, 0f)
            Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT)
        }

        speedY += (g_jump_Y + g) * delta
        g_jump_Y = 0f
        imgPosY += speedY * delta
        if (imgPosY <= min_pos_y){
            imgPosY = min_pos_y
            speedY = -speedY*(4f/5f)
        }

        //По  x
        if (Math.abs(g_jump_X) > 0f){
            sign = sign(g_jump_X)
            speedX =  g_jump_X * delta
            g_jump_X = 0f
        }

        speedX +=  sign(speedX) * delta * if (imgPosY == min_pos_y) b else bf

        if (sign(speedX) != sign){
            speedX = 0f
        }

        imgPosX += speedX * delta

        if ((imgPosX <= min_pos_x)){
                imgPosX = min_pos_x
            sign = - sign
            speedX = -speedX*(4f/5f)
        } else {
            if ((imgPosX >= max_pos_x)) {
                imgPosX = max_pos_x
                sign = -sign
                speedX = -speedX * (4f / 5f)
            }
        }


        if (eventproc.moveDown) {
            imgPosY -= dy * delta
        }
        if (eventproc.moveUp) {
            imgPosY += dy * delta
        }

        cameraZoom()

        mousePos()

        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
            boll.draw(batch, imgPosX, imgPosY)
        batch.end()
    }

override fun resize(width: Int, height: Int) {
    sw = width.toFloat()
    sh = height.toFloat()
    min_pos_y = -sh /2f
    min_pos_x = -sw/2f //- boll.sprite.width
    max_pos_x = sw/2f - boll.sprite.width
    hwRatio = sh/sw
    viewport.setWorldSize(sw, sh)
    viewport.setScreenSize(width, height)
    viewport.update(width, height)
    camera.update()
    println("FirstScreen resize: width = $sw  height = $sh")
}

override fun dispose() {
    font.dispose()
    batch.dispose()
    main.screen = main.mms
}

override fun pause() {
    println("FirstScreen paused")
}

override fun resume() {
    println("FirstScreen resume")
}

override fun hide() {
    println("FirstScreen hide")
}

override fun show() {
    Gdx.input.inputProcessor = eventproc
    println("FirstScreen show")
}

}