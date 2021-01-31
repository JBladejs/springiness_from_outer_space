package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor

class Player(var x: Float, var y: Float) : Entity {
    //TODO: investigate libGDX internal Asset Manager
    private val sprite = Texture(Gdx.files.internal("spaceship.png"))

    override fun update(delta: Float) {}

    override fun render(renderer: Renderer) {
        renderer.shapes.setColor(Color(255, 0, 0))
        renderer.shapes.set(ShapeRenderer.ShapeType.Filled)
        renderer.shapes.circle(x, y, 10f)
        renderer.sprites.draw(sprite, x, y)
    }

    override fun dispose() {
        sprite.dispose()
    }
}