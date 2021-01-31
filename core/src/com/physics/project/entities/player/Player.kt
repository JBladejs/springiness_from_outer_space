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

    init {
        sprite.setSize(100f, 100f)
        sprite.x = x - sprite.width / 2f
        sprite.y = y - sprite.height / 2f
        sprite.setOrigin(sprite.width / 2f, sprite.height / 2f)
        sprite.rotation = 90f
    }

    override fun update(delta: Float) {}

    override fun render(renderer: Renderer) {
        sprite.draw(renderer.sprites)
        renderer.shapes.set(ShapeRenderer.ShapeType.Filled)
        renderer.shapes.circle(x, y, 20f)
    }

    override fun dispose() {
        sprite.texture.dispose()
    }
}