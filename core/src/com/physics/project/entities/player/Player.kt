package com.physics.project.entities.player

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.setColor

class Player(var x: Float, var y: Float) : Entity {

    override fun update(delta: Float) {}

    override fun render(renderer: ShapeRenderer) {
        renderer.setColor(Color(10, 240, 10))
        renderer.set(ShapeRenderer.ShapeType.Point)
        renderer.point(x, y, 0f)
    }

}