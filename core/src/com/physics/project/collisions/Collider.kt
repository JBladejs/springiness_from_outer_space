package com.physics.project.collisions

interface Collider {
    fun collides(collider: Collider): Boolean
    var x: Float
    var y: Float
    val tag: CollisionTag
    var isColliding: Boolean
    var xHit: Float
    var yHit: Float
    var tagHit: CollisionTag
}