package com.physics.project.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.entities.Entity

class Renderer {
    val shapeRenderer = ShapeRenderer()
    val spriteBatch = SpriteBatch()
    init { shapeRenderer.setAutoShapeType(true) }
    fun render(entity: Entity) = entity.render(this)
    fun begin() {
        shapeRenderer.begin()
        spriteBatch.begin()
    }
    fun end() {
        shapeRenderer.end()
        spriteBatch.end()
    }
    fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
    }
}