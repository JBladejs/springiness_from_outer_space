package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Entity

internal data class MonsterPart(var x: Float, var y: Float) : Entity {
    val connections = Array<Spring>()

    override fun update() {
    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.circle(x, y, 20f)
    }
}