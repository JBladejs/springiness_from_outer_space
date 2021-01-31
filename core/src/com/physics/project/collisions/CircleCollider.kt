package com.physics.project.collisions

import com.badlogic.gdx.math.MathUtils
import kotlin.math.pow
import kotlin.math.sqrt

class CircleCollider(var x: Float, var y: Float, val radius: Float, val tag: CollisionTag) {
    var isColliding = false
    var xHit = 0f
    var yHit = 0f
    var tagHit = CollisionTag.EMPTY

    init {
        CollisionSystem.add(this)
    }

    fun update(x: Float, y: Float) {
        this.x = x
        this.y = y

    }

    fun collides(collider: CircleCollider): Boolean = sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) < radius + collider.radius
    fun collides(collider: SquareCollider): Boolean {
        var angleToCirc = MathUtils.atan2(yHit - y, xHit - x)
        var angleDiffrences = (angleToCirc - (collider.rotation*(3.14f/180f)))%6.28f
        var percent = MathUtils.sin(2f * angleDiffrences)
        var squareRadius = collider.size*0.5f+(collider.diagonal*percent)

        return sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) <  squareRadius + radius
    }
}