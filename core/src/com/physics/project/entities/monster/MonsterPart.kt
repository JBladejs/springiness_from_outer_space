package com.physics.project.entities.monster

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.utils.Array
import com.physics.project.collisions.CircleCollider
import com.physics.project.graphics.Color
import com.physics.project.Space
import com.physics.project.collisions.CollisionTag
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.fMod
import com.physics.project.util.setColor
import kotlin.math.abs


internal data class MonsterPart(var monster: Monster, var x: Float, var y: Float, var radius: Float, val color: Color = Color(10, 10, 240)) : Entity {
    companion object {
        private const val k = 0.001f
        private const val pushForce = 1f
        private const val hitForce = 5f
        private const val speed = 40f
        private const val maxSpeed = 350f
        private val headAnimation = Array<Texture>()
    }

    private val headX: Float
    private val headY: Float
    private val headSprite = Sprite(Texture("MonsterIdle/smokeMonster000.png"))
    private val armX: Float
    private val armY: Float
    private val armSprite = Sprite(Texture("arm.png"))
    private var centerX: Float
    private var centerY: Float
    private var sprite = armSprite

    private var animationTimer = 0

    override val layer: Int = 2

    private var vx = 0f
    private var vy = 0f
    private var dt = 0f

    val connections = Array<Spring>()
    private val collider = CircleCollider(x fMod Gdx.graphics.width, y fMod Gdx.graphics.height,radius,CollisionTag.ENEMY)

    var isHead = false

    operator fun times(part: MonsterPart) = (x - part.x) * (x - part.x) + (y - part.y) * (y - part.y)

    init {
        EntitySystem.add(this)
        headSprite.setSize(headSprite.width * radius * 0.008f, headSprite.height * radius * 0.008f)
        headX = headSprite.width * 0.5f
        headY = headSprite.height * 0.5f
        armSprite.setSize(armSprite.width * radius * 0.00689655172f, armSprite.height * radius * 0.00689655172f)
        armX = armSprite.width * 0.5f
        armY = armSprite.height * 0.5f

        if (headAnimation.size == 0) {
            for (i in 0..191) {
                headAnimation.add(Texture("MonsterIdle/smokeMonster${String.format("%03d", i)}.png"))
            }
        }

        centerX = armX
        centerY = armY
    }


    override fun update(delta: Float) {

        dt = delta
        connections.forEach {
            vx = vx * Space.airResistance + calcForceX(it)
            vy = vy * Space.airResistance + calcForceY(it)
        }

        if (abs(vx) >= maxSpeed)
            vx = if (vx > 0) maxSpeed else maxSpeed
        if (abs(vy) >= maxSpeed)
            vy = if (vy > 0) maxSpeed else maxSpeed

        x += vx * dt * speed
        y += vy * dt * speed

        collider.update(x fMod Gdx.graphics.width,y fMod Gdx.graphics.height)
        if(collider.isColliding){
            if(collider.tagHit == CollisionTag.ENEMY){
                push(collider.xHit,collider.yHit)
            }
            if (collider.tagHit == CollisionTag.BULLET) {
                hit(collider.xHit, collider.yHit)
                if(connections.isEmpty){
                    this.dispose()
                }
            }
            collider.isColliding = false
        }

        if (++animationTimer > 191) animationTimer = 0
        headSprite.texture = headAnimation[animationTimer]
        println(animationTimer)

        headSprite.setPosition((x - headX) fMod Gdx.graphics.width, (y - headY) fMod Gdx.graphics.height)
        armSprite.setPosition((x - armX) fMod Gdx.graphics.width, (y - armY) fMod Gdx.graphics.height)

        if (isHead) {
            sprite = headSprite
            centerX = headX
            centerY = headY
        } else {
            sprite = armSprite
            centerX = armX
            centerY = armY
        }
    }

    fun startHeadCreation(){
        this.monster.createHeads()
    }

    private fun move(x: Float, y: Float, to: Boolean) {
        val direction = atan2((x - collider.x),(y - collider.y))
        val force = if (to) 1 else -1
        vx += force * pushForce * sin(direction) * dt
        vy += force * pushForce * cos(direction) * dt
    }

    private fun push(fromX: Float, fromY: Float) = move(fromX, fromY, false)

    fun move(toX: Float, toY: Float) = move(toX, toY, true)

    private fun hit(x: Float, y: Float) {
        val direction = atan2((x - collider.x), (y - collider.y))
        vx -= hitForce * sin(direction)
        vy -= hitForce * cos(direction)
    }

    private fun calcForceX(spring: Spring): Float {
        val otherX = spring.getOtherEndXLocation(this)
        val moduleF = -k * (spring.length - spring.relaxLength)
        return moduleF * (this.x - otherX) / spring.length
    }

    private fun calcForceY(spring: Spring): Float {
        val otherY = spring.getOtherEndYLocation(this)
        val moduleF = -k * (spring.length - spring.relaxLength)
        return moduleF * (this.y - otherY) / spring.length
    }

    override fun render(renderer: Renderer) {
        sprite.draw(renderer.sprites)
        val modX = sprite.x
        val modY = sprite.y
        sprite.setPosition(modX - Gdx.graphics.width, modY)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX - Gdx.graphics.width, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY - Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX - Gdx.graphics.width, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)
        sprite.setPosition(modX + Gdx.graphics.width, modY + Gdx.graphics.height)
        sprite.draw(renderer.sprites)

    }

    override fun dispose() {
        collider.dispose()
        EntitySystem.dispose(this)
    }
}