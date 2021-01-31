package com.physics.project.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.entities.Entity

class Renderer {
    private val shapeRenderer = ShapeRenderer()
    private val spriteBatch = SpriteBatch()
    private var isShapeRendererActive = false
    private var isSpriteBatchActive = false

    val shapes : ShapeRenderer
        get() {
            if (isSpriteBatchActive) {
                spriteBatch.end()
                isSpriteBatchActive = false
            }
            if (!isShapeRendererActive) {
                shapeRenderer.begin()
                isShapeRendererActive = true
            }
            return shapeRenderer
        }

    val sprites : SpriteBatch
        get() {
            if (isShapeRendererActive) {
                shapeRenderer.end()
                isShapeRendererActive = false
            }
            if (!isSpriteBatchActive) {
                spriteBatch.begin()
                isSpriteBatchActive = true
            }
            return spriteBatch
        }

    init { shapeRenderer.setAutoShapeType(true) }

    fun render(entity: Entity) = entity.render(this)

    fun dispose() {
        if (isSpriteBatchActive) {
            spriteBatch.end()
        }
        if (isShapeRendererActive) {
            shapeRenderer.end()
        }
        shapes.dispose()
        sprites.dispose()
    }
}