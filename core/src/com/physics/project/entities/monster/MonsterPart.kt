package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.Space
import com.physics.project.entities.Entity
import com.physics.project.util.setColor


internal data class MonsterPart(var monster: Monster, var x: Float, var y: Float, var radius: Float, val color: Color = Color(10, 10, 240)) : Entity {
    companion object {
        private const val k = 0.001f
        private const val pushForce = 0.01f
        private const val speed = 10f
    }

    private var vx = 0f
    private var vy = 0f

    val connections = Array<Spring>()

    operator fun times(part: MonsterPart) = (x - part.x) * (x - part.x) + (y - part.y) * (y - part.y)

    override fun update(delta: Float) {
        connections.forEach {
            vx = vx * Space.airResistance + calcForceX(it)
            vy = vy * Space.airResistance + calcForceY(it)
        }
        x += vx * delta * speed
        y += vy * delta * speed
    }

    fun move(x: Float, y: Float, to: Boolean) {
        val direction = atan2((x - this.x), (y - this.y))
        val force = if (to) 1 else -1
        vx += force * pushForce * sin(direction)
        vy += force * pushForce * cos(direction)
    }

    fun push(fromX: Float, fromY: Float) = move(fromX, fromY, false)

    fun move(toX: Float, toY: Float) = move(toX, toY, true)

    private fun calcForceX(spring: Spring): Float {
        val otherX = spring.getOtherEndXLocation(this)
        val moduleF = -k * (spring.length - spring.relaxLength)
        return moduleF * (this.x - otherX) / spring.length
    }

    private fun calcForceY(spring: Spring): Float {
        val otherY = spring.getOtherEndYLocation(this)
        val moduleF = -k * (spring.length - spring.relaxLength)
        return moduleF * (this.y - otherY) / spring.length
    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.setColor(color)
        renderer.circle(x, y, radius)
    }
}