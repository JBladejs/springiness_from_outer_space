package com.game_physics.collisions.system

import com.badlogic.gdx.math.MathUtils.atan2
import com.badlogic.gdx.utils.Array

object CollisionSystem {
    private val colliders = Array<CircleCollider>()
    private val collisions = Array<Collision>()

    fun add(collider: CircleCollider) = colliders.add(collider)

    fun remove(collider: CircleCollider) = colliders.removeValue(collider, true)

    fun removeCollisionsForCollider(collider: CircleCollider) {
        for (collider2 in colliders) {
            val collision = Collision(collider, collider2)
            if (collisions.contains(collision, false))
                collisions.removeValue(collision, false)
        }
    }

    fun update() {
        for (i in 0 until colliders.size - 1) {
            for (j in i + 1 until colliders.size) {
                if (colliders[i].collides(colliders[j])) {
                    if (!collisions.contains(Collision(colliders[i], colliders[j]), false)) {
                        collisions.add(Collision(colliders[i], colliders[j]))
                        colliders[i].isColiding = true
                        colliders[i].tagHit = colliders[j].tag
                        colliders[i].xHit = colliders[j].x
                        colliders[i].yHit = colliders[j].y
                        colliders[j].isColiding = true
                        colliders[j].tagHit = colliders[i].tag
                        colliders[j].xHit = colliders[i].x
                        colliders[j].yHit = colliders[i].y
                        println("Collision! " + colliders[i].tag + " hits " + colliders[j].tag)
                    }
                } else {
                    val collision = Collision(colliders[i], colliders[j])
                    if (collisions.contains(collision, false))
                        collisions.removeValue(collision, false)
                }
            }
        }
    }
}