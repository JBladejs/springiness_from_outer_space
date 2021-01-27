package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity

internal data class MonsterPart(var x: Float, var y: Float, val color: Color = Color(10, 10, 240)) : Entity {
    val connections = Array<Spring>()

    override fun update() {
    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.setColor(color.red, color.blue, color.green, 1.0f)
        renderer.circle(x, y, 20f)
    }
}