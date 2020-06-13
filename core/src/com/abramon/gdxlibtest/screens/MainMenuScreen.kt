package com.abramon.gdxlibtest.screens

import com.abramon.gdxlibtest.Main
import com.abramon.gdxlibtest.controls.MainMenuEvent
import com.abramon.gdxlibtest.controls.MenuTypeSet
import com.abramon.gdxlibtest.model.ItemMenu
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport


class MainMenuScreen(mn: Main): Screen {
    private val useCam: Boolean = true
    private var mouseX: Float = 0f
    private var mouseY: Float = 0f
    private var allowRender: Boolean = true
    private var sw: Float = Gdx.app.graphics.width.toFloat()
    private var sh: Float = Gdx.app.graphics.height.toFloat()
    private var batch: SpriteBatch = SpriteBatch()
    private var font: BitmapFont = BitmapFont()
    private var camera: OrthographicCamera? = null
    private var viewport : Viewport? = null
    private val lineShape: ShapeRenderer = ShapeRenderer()

    val main: Main = mn
    var menu1: ItemMenu = ItemMenu(Texture("test1_off.png"),  Texture("test1_on.png"), useCam, sw, sh)
    var menu2: ItemMenu = ItemMenu(Texture("test2_off.png"),  Texture("test2_on.png"), useCam, sw, sh)
    var menuExit: ItemMenu = ItemMenu(Texture("exit_off.png"),  Texture("exit_on.png"), useCam, sw, sh)
    var menuTypeSet: MenuTypeSet = MenuTypeSet.NONE


    init {
        if (useCam) {
            this.camera = OrthographicCamera()
            this.viewport = ExtendViewport(sw, sh, camera!!)
        }
        this.font.data.scaleX = 1f
        this.font.data.scaleY = 1f
        ShaderProgram.pedantic = false
    }

    private val eventproc: MainMenuEvent = MainMenuEvent(this)

    private fun setFontColorX(){
        if (mouseX < 0)  font.color = Color.RED
        else  font.color = Color.GREEN
    }
    private fun setFontColorY(){
        if (mouseY < 0)  font.color = Color.RED
        else  font.color = Color.GREEN
    }

    private fun mouseXYDraw() {
        setFontColorX()
        font.draw(batch, "x: ${String.format("%.0f", mouseX)}", -sw/2f,  10f)

        setFontColorY()
        font.draw(batch, "y: ${String.format("%.0f", mouseY)}", -sw/2f, 60f)

        font.draw(batch, "sw: ${sw}", -sw/2f,  110f)
        font.draw(batch, "sh: ${sh}", -sw/2f, 150f)
    }

    private fun menuSelectDraw(){
        var x: Float = - menu1.getRect().width/2f
        var y: Float = 0f
        var y1: Float = -100f
        var y2: Float = -200f

        if (!useCam){
            x = (sw - menu1.getRect().width)/2f
            y = sh/2f + menu1.getRect().height
            y1 = sh/2f
            y2 = sh/2f - 2f*menu1.getRect().height
        }

        when (menuTypeSet){
            MenuTypeSet.BOLL -> {
                menu1.draw(batch, x, y, false, sw, sh)
                menu2.draw(batch, x, y1, Sw = sw, Sh = sh)
                menuExit.draw(batch, x, y2, Sw = sw, Sh = sh)
            }
            MenuTypeSet.TEST2 -> {
                menu1.draw(batch, x, y, Sw = sw, Sh = sh)
                menu2.draw(batch, x, y1, false, sw, sh)
                menuExit.draw(batch, x, y2, Sw = sw, Sh = sh)
            }
            MenuTypeSet.EXIT -> {
                menu1.draw(batch, x, y, Sw = sw, Sh = sh)
                menu2.draw(batch, x, y1, Sw = sw, Sh = sh)
                menuExit.draw(batch, x, y2, false, sw, sh)
            }
            else -> {
                menu1.draw(batch, x, y, Sw = sw, Sh = sh)
                menu2.draw(batch, x, y1, Sw = sw, Sh = sh)
                menuExit.draw(batch, x, y2, Sw = sw, Sh = sh)
            }
        }
    }

    fun mouseMoved(x:  Int, y: Int){
        mouseX = x.toFloat()
        mouseY = y.toFloat()
    }

    override fun render(delta: Float) {
        if (!allowRender) { return }

 //       if (Gdx.app.type == Application.ApplicationType.Android){
            Gdx.gl.glClearColor(0.200f, 0.201f, 0.200f, 0f)
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
//        } else {
//            Gdx.gl30.glClearColor(0.600f, 0.601f, 0.600f, 0f)
//            Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT)
//        }

        camera!!.update()
        lineShape.projectionMatrix = camera!!.combined

        lineShape.begin(ShapeRenderer.ShapeType.Line)
        lineShape.color = Color.RED
        lineShape.line(0f, sh/2f, sw, sh/2f)
        lineShape.end()


        if (useCam) {
            camera!!.update()
            batch.projectionMatrix = camera!!.combined
        }
        batch.begin()
            menuSelectDraw()
        batch.end()

        //координаты мыши
        batch.begin()
        mouseXYDraw()
        batch.end()
    }

    override fun hide() {
        allowRender = false
        println("MainMenu hide")
    }

    override fun show() {
        allowRender = true
        Gdx.input.inputProcessor = eventproc
        println("MainMenu show")
    }

    override fun pause() {
        allowRender = false
        println("MainMenu paused")
    }

    override fun resume() {
        allowRender = true
        println("MainMenu resume")
    }

    override fun resize(width: Int, height: Int) {
        sw = width.toFloat()
        sh = height.toFloat()
        if (useCam) {
            viewport!!.setWorldSize(sw, sh)
            viewport!!.setScreenSize(width, height)
            viewport!!.update(width, height)
            camera!!.update()
        }

        println("MainMenu resize")
    }

    override fun dispose() {
        allowRender = false
        font.dispose()
//        batch.dispose()
        //textBatch!!.dispose()
        println("MainMenu dispose")
    }

}