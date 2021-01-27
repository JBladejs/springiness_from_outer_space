package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.setColor
import kotlin.math.sqrt

internal data class Spring(val part1: MonsterPart, val part2: MonsterPart) : Entity {
    //TODO: verify and refactor
    private val maxLength = 200f
    var length = 100f
    val relaxLength = 100f
    var teared = false

    init {
        //TODO: check if it needs encapsulation
        part1.connections.add(this)
        part2.connections.add(this)
    }

    fun getOtherEndXLocation(part: MonsterPart): Float = if(part1 == part) part2.x else part1.x

    fun getOtherEndYLocation(part: MonsterPart): Float = if(part1 == part) part2.y else part1.y

    override fun update() {
        length = sqrt((part1.x - part2.x) * (part1.x - part2.x) + (part1.y - part2.y) * (part1.y - part2.y))
        if (length > maxLength) {
            part1.connections.removeValue(this, true)
            part2.connections.removeValue(this, true)
            teared = true
        }
        println("$length $teared")
    }

    override fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Line)
        renderer.setColor(Color(255,255,255))
        renderer.rectLine(part1.x, part1.y, part2.x, part2.y, 10f)
    }
}