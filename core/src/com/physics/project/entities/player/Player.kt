package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils.*
import com.physics.project.Space
import com.physics.project.collisions.CircleCollider
import com.physics.project.collisions.CollisionTag
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.degreesToRadians

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

    private var hp = 10f
    private var damagetimer = 0f
    private val damageDelay = 1f

    private var shootTimer = 0f
    private val shootDelay = 1f

    private val collider = CircleCollider(x % Gdx.graphics.width, y % Gdx.graphics.height, 50f, CollisionTag.PLAYER)

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
        if (Gdx.input.isKeyPressed(SPACE)) {
            shoot()
        }

        if(shootTimer > 0){
            shootTimer -= delta
        }

        x += vx * delta
        y += vy * delta

        collider.update(x % Gdx.graphics.width, y % Gdx.graphics.height)

        //TODO: Make damage timer framerate independent
        if (damagetimer > 0) damagetimer -= delta

        if (collider.isColliding && collider.tagHit == CollisionTag.ENEMY && damagetimer <= 0) {
            damagetimer = damageDelay
            if (--hp <= 0) Gdx.app.exit()
            collider.isColliding = false
        }

        sprite.x = (x - centerX) % Gdx.graphics.width
        sprite.y = (y - centerY) % Gdx.graphics.height
        sprite.rotation = rotation
    }

    //TODO: make shooting framerate independent
    private fun shoot(){
        if(shootTimer <= 0) {
            Bullet(x % Gdx.graphics.width, y % Gdx.graphics.height, degreesToRadians(rotation))
            shootTimer = shootDelay
        }
    }

    override fun render(renderer: Renderer) {
        sprite.draw(renderer.sprites)
        val modX = sprite.x
        val modY = sprite.y
        //TODO: decrease the number of renders
        //TODO: refactor this
        sprite.setPosition(modX - Gdx.graphics.width, modY)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)

        //TODO: make normal HUD
        renderer.shapes.setColor(Color.DARK_GRAY)
        renderer.shapes.rect(50f,50f,500f,20f)
        renderer.shapes.setColor(Color.RED)
        renderer.shapes.rect(50f,50f,50f * hp,20f)
    }

    override fun dispose() {
        EntitySystem.dispose(this)
        sprite.texture.dispose()
    }
}