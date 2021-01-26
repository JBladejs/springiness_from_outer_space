package com.physics.project

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class SpringinessGame : Game() {
    lateinit var renderer: ShapeRenderer

    override fun create() {
        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)
        screen = GameScreen(this)
    }

    override fun dispose() {
        screen.dispose()
        renderer.dispose()
    }
}