package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Entity

internal data class Spring(val part1: MonsterPart, val part2: MonsterPart) : Entity {
    init {
        part1.connections.add(this)
        part2.connections.add(this)
    }

    override fun update() {
    }

    override fun render(renderer: ShapeRenderer) {
    }
}