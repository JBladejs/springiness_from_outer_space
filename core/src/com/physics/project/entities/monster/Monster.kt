package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.render
import kotlin.math.cos
import kotlin.math.sin

class Monster(var x: Float, var y: Float, var startingSize: Float,var tentacleAmount: Int, var tentacleLength: Int ) : Entity {
    //TODO: massive refactor
    private val centralPart = MonsterPart(x, y, startingSize, Color(240, 10, 10))
    private val parts = Array<MonsterPart>()
    private val springs = Array<Spring>()
    private val v = 5f

    private val minSize = 10f

    init {
        parts.add(centralPart)          //Dodawanie kolejnych części do tablicy następuje poziomami długości tzn. pierwsze są te najbliżej głowy później te trochę dalej itd.
        for( i in 0..tentacleLength-1) {
            for( j in 1..tentacleAmount) {
                var currentLengthSize = startingSize * ((tentacleLength-i-1).toFloat()/tentacleLength) + (minSize*((i+1f)/tentacleLength))
                parts.add(MonsterPart(x + 110f * (i+1) * sin(2f * Math.PI / tentacleAmount * j).toFloat(), y + 110f * (i+1) * cos(2f * Math.PI / tentacleAmount * j).toFloat(),currentLengthSize))
                if(i>0)
                    springs.add(Spring(parts[j+((i-1)*tentacleAmount)], parts[j+(i*tentacleAmount)]))
                else
                    springs.add(Spring(parts[0], parts[j+(i*tentacleAmount)]))
            }
        }
    }

    override fun update() {
        parts.forEach { it.update() }
        for (i in springs.size - 1 downTo 0) {
            springs[i].update()
            if (springs[i].teared) springs.removeIndex(i)
        }
        x = centralPart.x
        y = centralPart.y
    }

    override fun render(renderer: ShapeRenderer) {
        parts.forEach { renderer.render(it) }
        springs.forEach { renderer.render(it) }
    }
}