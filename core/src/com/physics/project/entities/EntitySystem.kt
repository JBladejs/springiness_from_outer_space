package com.physics.project.entities

import com.badlogic.gdx.utils.Array
import com.physics.project.graphics.Renderer

object EntitySystem {
    private val entities = Array<Entity>()

    fun add(entity: Entity) = entities.add(entity)

    fun dispose(entity: Entity) = entities.removeValue(entity, true)

    fun render(renderer: Renderer) {
        for (i in (entities.size - 1) downTo 0) {
            renderer.render(entities[i])
        }
    }

    fun update(delta: Float) = entities.forEach{ it.update(delta) }

    fun disposeAll() = entities.forEach { it.dispose() }
}