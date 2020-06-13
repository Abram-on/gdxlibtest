package com.abramon.gdxlibtest.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle

class Rect(Img: Texture, x: Float, y: Float) {
    private val img: Texture = Img
    var sprite: Sprite = Sprite(img)
    var bounds: Circle = Circle(x, y, img.width/2f)

    init {
        this.sprite.setSize(img.width.toFloat(), img.height.toFloat())
    }

    fun draw(batch: SpriteBatch, x: Float, y: Float ){
        this.sprite.setPosition(x, y)
        this.bounds.setPosition(x, y)
        this.sprite.draw(batch)
    }

}
