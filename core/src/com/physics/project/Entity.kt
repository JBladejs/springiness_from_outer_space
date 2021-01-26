package com.physics.project

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface Entity {
    fun update()
    fun render(renderer: ShapeRenderer)
}

fun ShapeRenderer.render(entity: Entity) = entity.render(this)