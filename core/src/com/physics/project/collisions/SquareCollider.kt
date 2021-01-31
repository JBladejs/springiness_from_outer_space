package com.physics.project.collisions

import com.badlogic.gdx.math.MathUtils.*
import kotlin.math.pow
import kotlin.math.sqrt

class SquareCollider(var x: Float, var y: Float, var size: Float,var rotation: Float, val tag: CollisionTag) {
    var isColliding = false
    var xHit = 500f
    var yHit = 600f
    var tagHit = CollisionTag.EMPTY
    val diagonal = sqrt(2*size.pow(2))- (size*0.5f)

    init {

        this.size = size*0.5f
        this.rotation = rotation*(3.14f/180f)
        //CollisionSystem.add(this)
    }

    fun update(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun collides(collider: CircleCollider): Boolean {
        var angleToCirc = atan2(yHit - y,xHit - x)
        var angleDiffrences = (angleToCirc - rotation)%6.28f
        var percent = sin(2f*angleDiffrences)
        var squareRadius = size*0.5f+(diagonal*percent)

        return sqrt((collider.x - x).pow(2) + (collider.y - y).pow(2)) <  squareRadius + collider.radius
    }
}