package com.physics.project

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.graphics.Renderer

object Hud {
    var maxHealth = 0
    var currentHealth = 0
    var currentBullets = 0
    var maxBullets = 0
    var bulletRealoadPercent = 0f

    var maxMonsterHealth = 0
    var currentMonsterHealth = 0


    fun update(){

    }

    fun render(renderer: Renderer){

        renderer.shapes.setColor(Color.DARK_GRAY)
        for(i in 1..maxBullets)
            renderer.shapes.rect(43f + (7f * i), 83f, 5f, 20f)
        for (i in 1..maxHealth)
            renderer.shapes.rect(25f + (25f * i), 50f, 20f, 20f)
        renderer.shapes.rect(50f, 73f, 50f, 8f)

        renderer.shapes.set(ShapeRenderer.ShapeType.Filled)

        renderer.shapes.setColor(Color.RED)
        for (i in 1..currentHealth)
            renderer.shapes.rect(25f + (25f * i), 50f, 20f, 20f)


        renderer.shapes.setColor(Color.WHITE)
        for(i in 1..currentBullets)
            renderer.shapes.rect(43f + (7f * i), 83f, 5f, 20f)
        renderer.shapes.rect(50f, 73f, 50f * (1f - bulletRealoadPercent), 8f)
    }
}