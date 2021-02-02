package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.*
import com.physics.project.Space
import com.physics.project.collisions.CircleCollider
import com.physics.project.collisions.CollisionTag
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.degreesToRadians
import com.physics.project.util.fMod
import kotlin.math.abs

class Player(var x: Float, var y: Float) : Entity {
    companion object {
        private const val speed = 200f

        private const val rotationSpeed = 100f
        private const val stoppingSpeed = 60f
        private const val maxSpeed = 300f
    }

    override val layer: Int = 3

    //TODO: investigate libGDX internal Asset Manager
    private val sprite = Sprite(Texture("spaceship.png"))
    private val centerX: Float
    private val centerY: Float
    private var rotation = 0f
    private var vx = 0f
    private var vy = 0f

    private var hp = 10
    private var damagetimer = 0f
    private val damageDelay = 1f

    private var shootTimer = 0f
    private val shootDelay = 0.4f

    private val collider = CircleCollider(x fMod Gdx.graphics.width, y fMod Gdx.graphics.height, 50f, CollisionTag.PLAYER)

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
                vx += sin * Space.airResistance * delta * speed
                vy -= cos * Space.airResistance * delta * speed
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

        if (shootTimer > 0) {
            shootTimer -= delta
        }

        if (abs(vx) >= maxSpeed)
            vx = if (vx > 0) maxSpeed else -maxSpeed
        if (abs(vy) >= maxSpeed)
            vy = if (vy > 0) maxSpeed else -maxSpeed

        x += vx * delta
        y += vy * delta

        collider.update(x fMod Gdx.graphics.width, y fMod Gdx.graphics.height)

        if (damagetimer > 0) damagetimer -= delta

        if (collider.isColliding && collider.tagHit == CollisionTag.ENEMY && damagetimer <= 0) {
            damagetimer = damageDelay
            if (--hp <= 0) Gdx.app.exit()
            collider.isColliding = false
        }

        sprite.x = (x - centerX) fMod Gdx.graphics.width
        sprite.y = (y - centerY) fMod Gdx.graphics.height
        sprite.rotation = rotation
    }

    private fun shoot() {
        if (shootTimer <= 0) {
            Bullet(x fMod Gdx.graphics.width, y fMod Gdx.graphics.height, degreesToRadians(rotation))
            
            shootTimer = shootDelay
        }
    }

    override fun render(renderer: Renderer) {
        //TODO: make normal HUD
        renderer.shapes.set(ShapeRenderer.ShapeType.Filled)
        renderer.shapes.setColor(Color.DARK_GRAY)
        for (i in 0..9)
            renderer.shapes.rect(50f + (25f * i), 50f, 20f, 20f)
        renderer.shapes.rect(50f, 75f, 100f, 20f)
        renderer.shapes.setColor(Color.RED)
        for (i in 0..hp - 1)
            renderer.shapes.rect(50f + (25f * i), 50f, 20f, 20f)
        renderer.shapes.setColor(Color.WHITE)
        renderer.shapes.rect(50f, 75f, 100f * (1 - shootTimer), 20f)

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
        sprite.setPosition(modX - Gdx.graphics.width, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX - Gdx.graphics.width, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)
    }

    override fun dispose() {
        EntitySystem.dispose(this)
        sprite.texture.dispose()
    }
}