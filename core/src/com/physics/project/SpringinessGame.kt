package com.physics.project

import com.badlogic.gdx.Game
import com.physics.project.graphics.Renderer

class SpringinessGame : Game() {
    lateinit var renderer: Renderer

    override fun create() {
        renderer = Renderer()
        screen = GameScreen(this)
    }

    override fun dispose() {
        screen.dispose()
        renderer.dispose()
    }
}