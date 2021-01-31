package com.physics.project.entities.monster

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.physics.project.Color
import com.physics.project.entities.Entity
import com.physics.project.util.render
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

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
        parts.forEach {
            it.update()
        }
        //Collision check TODO: verify if it's good enough
        for (i in 0 until parts.size - 1) {
            for (j in i + 1 until parts.size) {
                if(parts[j].radius + parts[i].radius > sqrt((parts[j].x - parts[i].x)*(parts[j].x - parts[i].x)+((parts[j].y - parts[i].y) * (parts[j].y - parts[i].y)))){
                    parts[j].push(parts[i].x,parts[i].y)
                    parts[i].push(parts[j].x,parts[j].y)
                }
            }
        }
        for (i in springs.size - 1 downTo 0) {
            springs[i].update()
            if (springs[i].teared) springs.removeIndex(i)
        }
        x = centralPart.x
        y = centralPart.y
    }

    override fun render(renderer: ShapeRenderer) {
        springs.forEach { renderer.render(it) }
        parts.forEach { renderer.render(it) }
    }
}