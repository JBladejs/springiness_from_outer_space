package com.physics.project.collisions

interface Collider {
    var x: Float
    var y: Float
    val tag: CollisionTag
    var isColliding: Boolean
    var xHit: Float
    var yHit: Float
    var tagHit: CollisionTag
    fun collides(collider: Collider): Boolean
    fun dispose()
}