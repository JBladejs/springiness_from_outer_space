package com.physics.project.collisions

import com.badlogic.gdx.math.MathUtils.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class BoxCollider(override var x: Float, override var y: Float, var width: Float, var height: Float, var rotation: Float, override val tag: CollisionTag): Collider {
    override var isColliding = false
    override var xHit = 0f
    override var yHit = 0f
    override var tagHit = CollisionTag.EMPTY
    val diagonal = sqrt(width.pow(2)+height.pow(2))*0.5f
    var halfSize = 0f

    init {
        halfSize = 0.5f* abs((width*cos(rotation)) + (height*sin(rotation)))
        this.rotation = rotation*(3.14f/180f)
        CollisionSystem.add(this)
    }

    fun update(x: Float, y: Float, rot: Float) {
        this.rotation = rot*(3.14f/180f)
        this.x = x
        this.y = y
    }

    private fun collides(collider: CircleCollider): Boolean {
        val angleToCirc = atan2(yHit - y,xHit - x)
        val angleDiffrences = (angleToCirc - rotation)%6.28f
        val percent = abs(sin(2f*angleDiffrences))
        halfSize = 0.5f* abs((width*cos(angleDiffrences)) + (height*sin(angleDiffrences)))
        val squareRadius = (halfSize*(1-percent)) + (diagonal*percent)

        return sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) <  squareRadius + collider.radius
    }

    private fun collides(collider: BoxCollider): Boolean {
        TODO("Square to square collisions are not implemented yet!!!")
    }

    override fun collides(collider: Collider): Boolean = if (collider is CircleCollider) collides(collider) else collides(collider as BoxCollider)

    override fun dispose() {
       CollisionSystem.remove(this)
    }
}