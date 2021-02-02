package com.physics.project.entities

import com.physics.project.graphics.Renderer

interface Entity {
    val layer: Int
    fun update(delta: Float)
    fun render(renderer: Renderer)
    fun dispose()
}