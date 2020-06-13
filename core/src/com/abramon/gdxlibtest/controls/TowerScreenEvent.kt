package com.abramon.gdxlibtest.controls

import com.abramon.gdxlibtest.Main
import com.abramon.gdxlibtest.model.Gun
import com.abramon.gdxlibtest.model.Tank
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable


class TowerScreenEvent(mn: Main): Stage() {
    private val main : Main = mn
    var cntGun: Int = 0
    var cntTank: Int = 0
   // var groupGun: GroupGuns = GroupGuns(this)
    var groupTank: Group = Group()
    var ctrlRightDown: Boolean = false

    init {
       // addActor(groupTank)

    }


//    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        return false
//    }
//
//    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
//        return true
//    }
//
//    override fun keyTyped(character: Char): Boolean {
//        return true
//    }
//
//    override fun scrolled(amount: Int): Boolean {
//        return true
//    }

    override fun keyUp(keycode: Int): Boolean {
        val chTank = if (groupTank.children.isEmpty)
                                if (actors.isEmpty) null
                                else getFirstTank(root)
                            else getFirstTank(groupTank)

        when (keycode) {
            Input.Keys.UP -> {
                chTank!!.forwardMove = false
                return true
            }
            Input.Keys.DOWN -> {
                chTank!!.backwardMove = false
                return true
            }
            Input.Keys.LEFT -> {
                chTank!!.leftMove = false
                return true
            }
            Input.Keys.RIGHT -> {
                chTank!!.rightMove = false
                return true
            }
            Input.Keys.CONTROL_RIGHT -> {
                ctrlRightDown = false
                return true
            }
        }

        return false
    }


    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun hit(stageX: Float, stageY: Float, touchable: Boolean): Actor {
        println("root x=${root.x}, y=${root.y}, width=${root.width}, height=${root.height}, stageX=$stageX, stageY=$stageY ")
        //val v2: Vector2 = root. parentToLocalCoordinates(Vector2(stageX, stageY))
        //val v2 = stageToScreenCoordinates(Vector2(stageX, stageY))
        //val v2: Vector2 = Vector2(stageX, stageY)
        var v2: Vector2? = null//= root.localToActorCoordinates(Vector2(stageX, stageY))

        actors.items.forEachIndexed { index, actor ->
            if (actor is Gun) {
//                v2 = root.localToActorCoordinates(actor, Vector2(stageX, stageY))
                //близко, но все равно не верно
                v2 = root.stageToLocalCoordinates(Vector2(stageX, stageY))

                if (actor.circleAim.radius >= v2!!.dst(actor.x, actor.y))
                   println("Stage hit name=${actor.name} ")
           }
        }
        return root
    }

    private fun getFirstTank(act: Group): Tank?{
        for (item in act.children ){
            if (item is Tank) return item
        }
        return null
    }

    override fun keyDown(keycode: Int): Boolean {
        val chTank = if (groupTank.children.isEmpty)
                                if (actors.isEmpty) null
                                else getFirstTank(root)
                            else getFirstTank(groupTank)

        when (keycode) {
            Input.Keys.ESCAPE -> {
                main.screen = main.mms
                return true
            }
            Input.Keys.UP -> {
                chTank!!.forwardMove = true
                return true
            }
            Input.Keys.DOWN -> {
                chTank!!.backwardMove = true
                return true
            }
            Input.Keys.LEFT -> {
                chTank!!.leftMove = true
                return true
            }
            Input.Keys.RIGHT -> {
                chTank!!.rightMove = true
                return true
            }
            Input.Keys.CONTROL_RIGHT -> {
                ctrlRightDown = true
                return true
            }
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.LEFT && ctrlRightDown) {
            cntGun += 1
            val gun: Gun = Gun(Texture("gun.png"), "gun$cntGun")
            //groupGun.addActor(gun)
            addActor(gun)
            gun.setGun(screenX.toFloat(), screenY.toFloat())
            return true
        }

        if (button == Input.Buttons.RIGHT && ctrlRightDown) {
            cntTank += 1
            val tank: Tank = Tank(Texture("tank.png"), "tank$cntTank")
            //groupTank.addActor(tank)
            addActor(tank)
            tank.set(screenX.toFloat(), screenY.toFloat())
            return true
        }

//        groupTank.children.forEach {
//            addAction(Actions.  moveTo(x, y,0.5f, Interpolation.linear))
//       }
        return true
    }
}