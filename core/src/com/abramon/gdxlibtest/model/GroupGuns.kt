package com.abramon.gdxlibtest.model

import com.badlogic.gdx.scenes.scene2d.*

class GroupGuns(stage: Stage): Group() {

    init {
        this.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                println("Gun group touchUp x=$x, y=$y")
            }

            override fun mouseMoved(event: InputEvent?, x: Float, y: Float): Boolean {
                println("Gun group mouse moved x=$x, y=$y")
                return false
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                println("Gun group touchDown x=$x, y=$y")
                return true
            }

            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                println("Gun group enter x=$x, y=$y")
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                println("Gun group exit x=$x, y=$y")
            }
        }
        )
        this.name = "GroupGuns"
        this.stage = stage
        this.width = stage.width
        this.height = stage.width
        this.x = 0f
        this.y = 0f
        this.touchable = Touchable.enabled
        this.isVisible = true
        //stage.addActor(this)

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

override fun hit(x: Float, y: Float, touchable: Boolean): Actor {
// println("GroupGuns hit x=$x, y=$y")

    return super.hit(x, y, touchable)
}

}