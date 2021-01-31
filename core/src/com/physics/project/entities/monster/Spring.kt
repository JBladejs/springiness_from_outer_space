package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor
import kotlin.math.sqrt

internal data class Spring(val part1: MonsterPart, val part2: MonsterPart) : Entity {
    companion object {
        private const val maxLength = 200f
    }

    val relaxLength = 100f
    var length = 100f
    var teared = false

    init {
        EntitySystem.add(this)
        part1.connections.add(this)
        part2.connections.add(this)
    }

    fun getOtherEndXLocation(part: MonsterPart): Float = if (part1 == part) part2.x else part1.x

    fun getOtherEndYLocation(part: MonsterPart): Float = if (part1 == part) part2.y else part1.y

    override fun update(delta: Float) {
        length = sqrt(part1 * part2)
        if (length > maxLength) {
            part1.connections.removeValue(this, true)
            part2.connections.removeValue(this, true)
            teared = true
        }
    }

    override fun render(renderer: Renderer) {
        renderer.shapes.set(ShapeRenderer.ShapeType.Line)
        renderer.shapes.setColor(Color(255, 255, 255))
        renderer.shapes.rectLine(part1.x, part1.y, part2.x, part2.y, 10f)
    }

    override fun dispose() {
        EntitySystem.dispose(this)
    }
}