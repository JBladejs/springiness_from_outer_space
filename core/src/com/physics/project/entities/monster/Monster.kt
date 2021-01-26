package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Entity
import com.physics.project.render

class Monster : Entity {
    private val parts = Array<MonsterPart>()
    private val springs = Array<Spring>()

    init {
        parts.add(MonsterPart(20f, 25f))
        parts.add(MonsterPart(80f, 105f))
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