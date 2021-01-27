package com.physics.project.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface Entity {
    fun update()
    fun render(renderer: ShapeRenderer)
}