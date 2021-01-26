package com.physics.project

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Array
import com.physics.project.entities.monster.Monster

class GameScreen(private val game: SpringinessGame) : Screen {
    private val entities = Array<Entity>()

    init {
        entities.add(Monster())
    }

    private fun update(delta: Float) {
        entities.forEach { it.update() }
    }

    override fun render(delta: Float) {
        entities.forEach { game.renderer.render(it) }
        update(delta)
    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {}

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun show() {}
}