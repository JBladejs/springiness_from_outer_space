package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.cos
import com.badlogic.gdx.math.MathUtils.sin
import com.physics.project.Space
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.degreesToRadians
import com.physics.project.util.radiansToDegrees
import com.physics.project.util.setColor

class Player(var x: Float, var y: Float) : Entity {
    companion object {
        private const val speed = 200f
        private const val rotationSpeed = 100f
        private const val stoppingSpeed = 60f
    }
    //TODO: investigate libGDX internal Asset Manager
    private val sprite = Sprite(Texture("spaceship.png"))
    private val centerX: Float
    private val centerY: Float
    private var rotation = 0f
    private var vx = 0f
    private var vy = 0f

    init {
        EntitySystem.add(this)
        sprite.setSize(100f, 100f)
        centerX = sprite.width * 0.5f
        centerY = sprite.height * 0.5f
        sprite.setOrigin(centerX, centerY)
    }

    override fun update(delta: Float) {
        //TODO: add a proper InputProcessor
        if (Gdx.input.isKeyPressed(W) || Gdx.input.isKeyPressed(S)) {
            val direction = degreesToRadians(rotation)
            val sin = sin(direction)
            val cos = cos(direction)
            if (Gdx.input.isKeyPressed(S)) {
//                println(delta * stoppingSpeed)
                vx *= 1f - delta
                vy *= 1f - delta
            } else {
                vx -= sin * Space.airResistance * delta * speed
                vy += cos * Space.airResistance * delta * speed
            }
        }
        if (Gdx.input.isKeyPressed(A)) rotation += rotationSpeed * delta
        if (Gdx.input.isKeyPressed(D)) rotation -= rotationSpeed * delta
        if (Gdx.input.isKeyPressed(SPACE)) Bullet(x, y, degreesToRadians(rotation))

        x += vx * delta
        y += vy * delta

        sprite.x = x - centerX
        sprite.y = y - centerY
        sprite.rotation = rotation
    }

    override fun render(renderer: Renderer) {
        sprite.draw(renderer.sprites)
    }

    override fun dispose() {
        EntitySystem.dispose(this)
        sprite.texture.dispose()
    }
}