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

    override fun update(delta: Float) {}

    override fun render(renderer: Renderer) {
        sprite.rotation = 30f
        sprite.x = x
        sprite.y = y
        sprite.draw(renderer.sprites)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }
}