package com.physics.project

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Array
import com.physics.project.collisions.CollisionSystem
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.entities.monster.Monster
import com.physics.project.entities.player.Bullet
import com.physics.project.entities.player.Player

class GameScreen(private val game: SpringinessGame) : Screen {
    val sprite = Texture(Gdx.files.internal("spaceship.png"))
    val backgroundSprite = Sprite(Texture(Gdx.files.internal("background.png")))

    init {
        //TODO: Implement proper entity system
        val player = Player(600f, 600f)
        Monster(400f, 400f, player,30f, 5, 4)
    }

    private fun update(delta: Float) {
        EntitySystem.update(delta)
        CollisionSystem.update()
        Bullet.animate()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        EntitySystem.render(game.renderer)
        Hud.render(game.renderer)
        backgroundSprite.setSize(Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat())
        backgroundSprite.draw(game.renderer.sprites)
        update(delta)
    }

    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
        EntitySystem.disposeAll()
    }

    override fun resume() {}
    override fun pause() {}
    override fun hide() {}
    override fun show() {}
}