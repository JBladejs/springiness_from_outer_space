package com.physics.project.entities.monster

import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.player.Player
import com.physics.project.graphics.Renderer

class Monster(x: Float, y: Float, val player: Player, startingSize: Float, tentacleAmount: Int, tentacleLength: Int) : Entity {
    val x: Float
        get() = centralPart.x
    val y: Float
        get() = centralPart.y

    private val centralPart = MonsterPart(this, x, y, startingSize, Color(240, 10, 10))
    private val parts = Array<MonsterPart>()
    private val springs = Array<Spring>()
//    private val v = 5f //not used yet

    private val minSize = 10f

    init {
        parts.add(centralPart)          //Dodawanie kolejnych części do tablicy następuje poziomami długości tzn. pierwsze są te najbliżej głowy później te trochę dalej itd.
        //TODO: add some random element to part generation
        for (i in 0 until tentacleLength) {
            for (j in 1..tentacleAmount) {
                val currentLengthSize = startingSize * ((tentacleLength - i - 1).toFloat() / tentacleLength) + (minSize * ((i + 1f) / tentacleLength))
                createPart(110f * (i + 1) * sin(2f * PI / tentacleAmount * j), 110f * (i + 1) * cos(2f * PI / tentacleAmount * j), currentLengthSize)
                if (i > 0) connect(parts[j + ((i - 1) * tentacleAmount)], parts[j + (i * tentacleAmount)])
                else connect(parts[0], parts[j + (i * tentacleAmount)])
            }
        }
    }

    private fun createPart(x: Float, y: Float, size: Float) = parts.add(MonsterPart(this, this.x + x, this.y + y, size))

    private fun connect(part1: MonsterPart, part2: MonsterPart) = springs.add(Spring(part1, part2))

    override fun update(delta: Float) {
        parts.forEach {
            it.update(delta)
        }
        for (i in springs.size - 1 downTo 0) {
            springs[i].update(delta)
            if (springs[i].teared) springs.removeIndex(i)
        }
        centralPart.move(player.x, player.y)
    }

    override fun render(renderer: Renderer) {
        springs.forEach { renderer.render(it) }
        parts.forEach { renderer.render(it) }
    }
}