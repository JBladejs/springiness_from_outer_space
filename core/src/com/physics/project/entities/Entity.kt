package com.physics.project.entities

import com.physics.project.graphics.Renderer

interface Entity {
    fun update(delta: Float)
    fun render(renderer: Renderer)
    fun dispose()
}