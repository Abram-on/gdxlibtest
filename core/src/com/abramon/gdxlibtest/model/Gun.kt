package com.abramon.gdxlibtest.model

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.utils.Align

class Gun(texture: Texture, nameIn: String): Actor() {

    private val sprite: TextureRegion = TextureRegion(texture)
    var circleFire: Circle = Circle(x, y, sprite.regionWidth.toFloat())
    var circleAim: Circle = Circle(x, y, 2f*sprite.regionWidth.toFloat())
    var sh: ShapeRenderer = ShapeRenderer()
    val pixmap: Pixmap = Pixmap(circleAim.radius.toInt()*2+1, circleAim.radius.toInt()*2+1, Pixmap.Format.RGBA8888)
    var event: Event = Event()


    init {
        isVisible = false
        //width = texture.width.toFloat()
        //height = texture.height.toFloat()
        //rotation = 0f
        this.name = nameIn
    }



    fun setGun(xIn: Float, yIn: Float){
        val v2: Vector2 = stage.screenToStageCoordinates(Vector2(xIn, yIn))
        width = sprite.regionWidth.toFloat()
        height = sprite.regionHeight.toFloat()
        setOrigin(Align.center)
        x = v2.x - originX
        y = v2.y - originY
        println("Gun set $xIn,  $yIn,  --->  ${x},  ${y} ")
        setBounds(x, y, width, height)
        circleFire.set(x + originX, y + originY, sprite.regionWidth.toFloat())
        circleAim.set(x + originX , y + originY , 2f*sprite.regionWidth.toFloat())
        isVisible = true
        touchable = Touchable.enabled

        println("Tank x=$x,  y=$y,  originX=$originX,  originY=$originY")
        this.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                println("Gun name=$name touchUp")
                //super.touchUp(event, x, y, pointer, button)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                println("Gun name=$name touchDown")
                return true//super.touchDown(event, x, y, pointer, button)
            }

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                println("Gun name=$name enter")
                //super.enter(event, x, y, pointer, fromActor)
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                println("Gun name=$name exit")
//                super.exit(event, x, y, pointer, toActor)
            }

        })
        touchable = Touchable.enabled
    }

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
        super.act(delta)
    }

    fun shDraw(batch: Batch){
        debug = true
        stage.setDebugInvisible(true)
        stage.isDebugAll = true
        sh.begin(ShapeRenderer.ShapeType.Line)
        sh.color = Color.GOLD
        sh.circle(circleAim.x, circleAim.y, circleAim.radius)
        sh.color = Color.BLUE
        sh.circle(circleFire.x, circleFire.y, circleFire.radius)
        sh.end()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch!!.draw(sprite, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        pixmap.setColor(Color.BLUE)
        pixmap.drawCircle(circleAim.radius.toInt(), circleAim.radius.toInt(), circleAim.radius.toInt())
        pixmap.setColor(Color.RED)
        pixmap.drawCircle(circleAim.radius.toInt(), circleAim.radius.toInt(), circleFire.radius.toInt())
        batch.draw(Texture(pixmap),circleAim.x - pixmap.width/2f, circleAim.y - pixmap.height/2f)

//        batch.end()
//        shDraw(batch)
//        batch.begin()
    }

    override fun hit(x: Float, y: Float, touchable: Boolean): Actor {
        println("$name hit x=$x, y=$y, touchable=$touchable")
        return this//super.hit(x, y, touchable)
    }


}