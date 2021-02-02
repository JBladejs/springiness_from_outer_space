package com.physics.project.entities.monster

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.utils.Array
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
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

    private val minSize = 10f

    init {
        EntitySystem.add(this)
        parts.add(centralPart)          //Dodawanie kolejnych części do tablicy następuje poziomami długości tzn. pierwsze są te najbliżej głowy później te trochę dalej itd.
        centralPart.isHead = true
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

    fun createHeads(){
        //var headlessParts = Array<MonsterPart>()
        parts.forEach{
            if(!checkHeadConnection(it)){
                //headlessParts.add(it)
                it.isHead = true
            }
        }
        /*
        headlessParts[0].isHead = true
        for (i in 1..headlessParts.size){
            if(!checkHeadConnection(headlessParts[i])){
                headlessParts[i].isHead = true
                break
            }
        }*/
    }

    private fun checkHeadConnection(part: MonsterPart): Boolean{
        var headFound = part.isHead
        if(!part.connections.isEmpty)
        for (i in 0..part.connections.size-1){
            if(headFound){
                break
            }
            if (part.connections[i].getOtherPart(part).isHead){
                headFound = true
            }
            else{
                headFound = checkHeadConnection( part.connections[i].getOtherPart(part), part )
            }
        }
        return headFound
    }

    private fun checkHeadConnection(part: MonsterPart, without: MonsterPart): Boolean{
        var headFound = false
        if(!part.connections.isEmpty)
        for (i in 0..part.connections.size-1){
            if (headFound) {
                break
            }
            if(part.connections[i].getOtherPart(part) != without) {
                if (part.connections[i].getOtherPart(part).isHead) {
                    headFound = true
                } else {
                    headFound = checkHeadConnection(part.connections[i].getOtherPart(part), part)
                }
            }
        }
        return headFound
    }

    private fun createPart(x: Float, y: Float, size: Float) = parts.add(MonsterPart(this, this.x + x, this.y + y, size))

    private fun connect(part1: MonsterPart, part2: MonsterPart) = springs.add(Spring(part1, part2))

    override fun update(delta: Float) {
        //centralPart.move(player.x % Gdx.graphics.width, player.y % Gdx.graphics.height)
        parts.forEach {
            if(it.isHead)
                it.move(player.x % Gdx.graphics.width, player.y % Gdx.graphics.height)
        }
    }


    override fun render(renderer: Renderer) {
//        parts.forEach { renderer.render(it) }
//        springs.forEach { renderer.render(it) }
    }

    override fun dispose() {
        EntitySystem.dispose(this)
        springs.forEach { it.dispose() }
        parts.forEach { it.dispose() }
    }
}