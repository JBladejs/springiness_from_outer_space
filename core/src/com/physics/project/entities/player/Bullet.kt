package com.physics.project.entities.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.cos
import com.badlogic.gdx.math.MathUtils.sin
import com.physics.project.collisions.CircleCollider
import com.physics.project.collisions.CollisionTag
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Color
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor

class Bullet(var x: Float, var y: Float, direction: Float) : Entity {
    private val vx: Float
    private val vy: Float

    override val layer: Int = 0

    companion object{
        const val radius = 10f
    }

    private val collider = CircleCollider(x, y, radius, CollisionTag.BULLET)

    init {
        EntitySystem.add(this)
        val speed = 500f
        vx = -sin(direction) * speed
        vy = cos(direction) * speed
    }

    override fun update(delta: Float) {
        x += vx * delta
        y += vy * delta
        collider.update(x, y)
        if(collider.isColliding){
            //println(collider.tagHit)
            if(collider.tagHit == CollisionTag.ENEMY){
                dispose()
            }
        }
        if (x >= Gdx.graphics.width || x <= 0f || y >= Gdx.graphics.height || y <= 0f)
            dispose()
    }

    override fun render(renderer: Renderer) {
        renderer.shapes.set(ShapeRenderer.ShapeType.Filled)
        renderer.shapes.setColor(Color(200, 50, 20))
        renderer.shapes.circle(x, y, 10f)
    }

    override fun dispose() {
        collider.dispose()
        EntitySystem.dispose(this)
    }
}