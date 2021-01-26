package com.physics.project

import com.badlogic.gdx.Screen

class GameScreen(private val game: SpringinessGame) : Screen {

    private fun update(delta: Float) {
        //logic
    }

    override fun render(delta: Float) {
        //graphics
        update(delta)
    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {}

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun show() {}
}