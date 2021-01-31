package com.physics.project.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface Entity {
    fun update(delta: Float)
    fun render(renderer: ShapeRenderer)
}