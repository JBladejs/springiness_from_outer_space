package com.physics.project.collisions

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
}