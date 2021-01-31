package com.physics.project.collisions

import com.badlogic.gdx.math.MathUtils
import kotlin.math.pow
import kotlin.math.sqrt

class CircleCollider(override var x: Float, override var y: Float, val radius: Float, override val tag: CollisionTag): Collider {
    override var isColliding = false
    override var xHit = 0f
    override var yHit = 0f
    override var tagHit = CollisionTag.EMPTY

    init {
        CollisionSystem.add(this)
    }

    fun update(x: Float, y: Float) {
        this.x = x
        this.y = y

    }

    private fun collides(collider: CircleCollider): Boolean = sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) < radius + collider.radius

    private fun collides(collider: SquareCollider): Boolean {
        val angleToCirc = MathUtils.atan2(yHit - y, xHit - x)
        val angleDiffrences = (angleToCirc - (collider.rotation*(3.14f/180f)))%6.28f
        val percent = MathUtils.sin(2f * angleDiffrences)
        val squareRadius = collider.size*0.5f+(collider.diagonal*percent)

        return sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) <  squareRadius + radius
    }

    override fun collides(collider: Collider): Boolean = if (collider is CircleCollider) collides(collider) else collides(collider as SquareCollider)
}