package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.render

class Monster(var x: Float, var y: Float) : Entity {
    //TODO: massive refactor
    private val centralPart = MonsterPart(x, y, Color(240, 10, 10))
    private val parts = Array<MonsterPart>()
    private val springs = Array<Spring>()

    init {
        parts.add(centralPart)
        parts.add(MonsterPart(x + 130f, y + 130f))
        springs.add(Spring(parts[0], parts[1]))
    }

    override fun update() {
        parts.forEach { it.update() }
        for (i in springs.size - 1 downTo 0) {
            springs[i].update()
            if (springs[i].teared) springs.removeIndex(i)
        }
    }

    override fun render(renderer: ShapeRenderer) {
        parts.forEach { renderer.render(it) }
        springs.forEach { renderer.render(it) }
    }
}