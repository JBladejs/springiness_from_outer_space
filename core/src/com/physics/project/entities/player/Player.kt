package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor

class Player(var x: Float, var y: Float) : Entity {
    //TODO: investigate libGDX internal Asset Manager
    private val sprite = Sprite(Texture("spaceship.png"))
    private val centerX: Float
    private val centerY: Float
    private var rotation = 0f

    init {
        sprite.setSize(100f, 100f)
        centerX = sprite.width * 0.5f
        centerY = sprite.height * 0.5f
        sprite.setOrigin(centerX, centerY)
    }

    override fun update(delta: Float) {
        sprite.x = x - centerX
        sprite.y = y - centerY
        sprite.rotation = rotation
    }

    override fun render(renderer: Renderer) {
        sprite.draw(renderer.sprites)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }
}