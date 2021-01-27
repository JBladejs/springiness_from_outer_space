package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.setColor

internal data class MonsterPart(var x: Float, var y: Float, val color: Color = Color(10, 10, 240)) : Entity {
    //TODO: move some of the properties to more general class/object
    private var k = 0.001f
    private var airRes = 0.999f
    private var vx = 0f
    private var vy = 0f

    val connections = Array<Spring>()

    //TODO: verify all of that and refactor

    override fun update() {
        connections.forEach {
            vx = vx * airRes + calcForceX(it)
            vy = vy * airRes + calcForceY(it)
        }
        x += vx
        y += vy
    }

    private fun calcForceX(spring: Spring): Float {
        val otherX = spring.getOtherEndXLocation(this)
        val moduleF = -k*(spring.length-spring.relaxLength)

        return moduleF * (this.x-otherX)/spring.length
    }

    private fun calcForceY(spring: Spring): Float {
        val otherY = spring.getOtherEndYLocation(this)
        val moduleF = -k*(spring.length-spring.relaxLength)

        return moduleF * (this.y-otherY)/spring.length
    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.setColor(color)
        renderer.circle(x, y, 20f)
    }
}