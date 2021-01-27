package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.render

class Monster(var x: Float, var y: Float) : Entity {
    private val centralPart = MonsterPart(x, y, Color(240, 10, 10))
    private val parts = Array<MonsterPart>()
    private val springs = Array<Spring>()

    init {
        parts.add(centralPart)
        parts.add(MonsterPart(x + 40f, y + 40f))
    }

    override fun update() {
        parts.forEach { it.update() }
        springs.forEach { it.update() }
    }

    override fun render(renderer: ShapeRenderer) {
        parts.forEach { renderer.render(it) }
        springs.forEach { renderer.render(it) }
    }
}