package com.physics.project.entities.player

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor

class Player(var x: Float, var y: Float) : Entity {
    override fun update(delta: Float) {}

    override fun render(renderer: Renderer) {
        renderer.shapeRenderer.setColor(Color(10, 240, 10))
        renderer.shapeRenderer.set(ShapeRenderer.ShapeType.Point)
        renderer.shapeRenderer.point(x, y, 0f)
    }
}