package com.abramon.gdxlibtest.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class ItemMenu(ImgOff: Texture, ImgOn: Texture, UseCam: Boolean = false, initSw: Float, initSh: Float) {
    private val useCam: Boolean = UseCam
    private val imgOff: Texture = ImgOff
    private val imgOn: Texture = ImgOn
    private var sprite: Sprite = Sprite(imgOff)
    private var bounds: Rectangle = Rectangle()
    private var preSw: Float = initSw
    private var preSh: Float = initSh

    fun draw(batch: SpriteBatch, X: Float, Y: Float, off: Boolean = true, Sw: Float = 0f, Sh: Float = 0f ){
        var x: Float = X
        var y: Float = Y
        var boundX: Float = X
        var boundY: Float = Y

        if (off) {
            sprite.texture = imgOff
        } else {
            sprite.texture = imgOn
        }
        if (useCam){
            boundX = Sw/2f + x
            boundY = Sh/2f - y - sprite.texture.height
        } else {
            //Не решено!
            boundX = x - x * (1 - preSw/Sw)
                    //x +=  preSw - Sw
            boundY = Sh - (y * (preSh/Sh)) - sprite.texture.height
        }

        this.sprite.setSize(sprite.texture.width.toFloat(), sprite.texture.height.toFloat())
        this.bounds.setSize(sprite.texture.width.toFloat(), sprite.texture.height.toFloat())
        this.sprite.setPosition(x, y)
        this.bounds.setPosition(boundX, boundY)

        this.sprite.draw(batch)

        preSw = Sw
        preSh = Sh
    }

    fun getRect(): Rectangle {
        return bounds//sprite.boundingRectangle
    }
}
