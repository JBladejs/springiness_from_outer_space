package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.setColor

internal data class MonsterPart(var x: Float, var y: Float, val color: Color = Color(10, 10, 240)) : Entity {
    private var k = 0.001f
    private var airRes = 0.999f
    private var vx = 0f
    private var vy = 0f
    //TODO: move some of the properties to more general class/object
    val connections = Array<Spring>()

    override fun update() {

    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.setColor(color)
        renderer.circle(x, y, 20f)
    }
}