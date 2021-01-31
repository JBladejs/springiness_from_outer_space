package com.physics.project

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.Array
import com.physics.project.collisions.CollisionSystem
import com.physics.project.entities.Entity
import com.physics.project.entities.monster.Monster
import com.physics.project.entities.player.Player
import com.physics.project.util.render

class GameScreen(private val game: SpringinessGame) : Screen {
    private val entities = Array<Entity>()

    init {
        //TODO: Implement proper entity system
        val player = Player(800f, 800f)
        entities.add(player)
        entities.add(Monster(400f, 400f, player,30f, 5, 4))
    }

    private fun update(delta: Float) {
        entities.forEach { it.update(delta) }
        CollisionSystem.update()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        game.renderer.begin()
        entities.forEach { game.renderer.render(it) }
        game.renderer.end()
        update(delta)
    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {}

    override fun resume() {}
    override fun pause() {}
    override fun hide() {}
    override fun show() {}
}