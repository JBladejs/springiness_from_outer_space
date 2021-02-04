package com.physics.project.entities.monster

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Hud
import com.physics.project.graphics.Color
import com.physics.project.entities.Entity
import com.physics.project.entities.EntitySystem
import com.physics.project.graphics.Renderer
import com.physics.project.util.setColor
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

internal data class Spring(val part1: MonsterPart, val part2: MonsterPart) : Entity {
    companion object {
        private const val maxLength = 200f
    }

    override val layer: Int = 1

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

    fun getOtherPart(part: MonsterPart): MonsterPart = if (part1 == part) part2 else part1

    override fun update(delta: Float) {
        length = sqrt(part1 * part2)
        if (length > maxLength) {
            part1.connections.removeValue(this, true)
            part2.connections.removeValue(this, true)
            //teared = true
            dispose()
            part1.startHeadCreation()
            Hud.currentMonsterHealth--
        }
    }

    override fun render(renderer: Renderer) {
        //TODO: Refactor all of this
        renderer.shapes.set(ShapeRenderer.ShapeType.Line)
        renderer.shapes.setColor(Color((length/maxLength*255f).toInt(), (length/maxLength*80f).toInt(), (length/maxLength*80f).toInt()))
        //TODO: There's gotta be a better way to do this...
        val xa: Int = part1.x.toInt() / Gdx.graphics.width
        val xb: Int = part2.x.toInt() / Gdx.graphics.width
        val ya: Int = part1.y.toInt() / Gdx.graphics.height
        val yb: Int = part2.y.toInt() / Gdx.graphics.height
        var renderDiffX: Float = if (abs(xa) > abs(xb)) xb.toFloat() else xa.toFloat()
        var renderDiffY: Float = if (abs(ya) > abs(yb)) yb.toFloat() else ya.toFloat()
        renderDiffX *= Gdx.graphics.width
        renderDiffY *= Gdx.graphics.height
        if (renderDiffX < 0f) renderDiffX -= Gdx.graphics.width
        if (renderDiffY < 0f) renderDiffY -= Gdx.graphics.height
        //TODO: decrease the number of renders
        renderer.shapes.rectLine(part1.x - renderDiffX, part1.y - renderDiffY, part2.x - renderDiffX, part2.y - renderDiffY, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX - Gdx.graphics.width, part1.y - renderDiffY, part2.x - renderDiffX - Gdx.graphics.width, part2.y - renderDiffY, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX + Gdx.graphics.width, part1.y - renderDiffY, part2.x - renderDiffX + Gdx.graphics.width, part2.y - renderDiffY, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX, part1.y - renderDiffY - Gdx.graphics.height, part2.x - renderDiffX, part2.y - renderDiffY - Gdx.graphics.height, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX, part1.y + renderDiffY + Gdx.graphics.height, part2.x - renderDiffX, part2.y - renderDiffY + Gdx.graphics.height, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX - Gdx.graphics.width, part1.y - renderDiffY - Gdx.graphics.height, part2.x - renderDiffX - Gdx.graphics.width, part2.y - renderDiffY - Gdx.graphics.height, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX + Gdx.graphics.width, part1.y - renderDiffY - Gdx.graphics.height, part2.x - renderDiffX + Gdx.graphics.width, part2.y - renderDiffY - Gdx.graphics.height, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX - Gdx.graphics.width, part1.y - renderDiffY + Gdx.graphics.height, part2.x - renderDiffX - Gdx.graphics.width, part2.y - renderDiffY + Gdx.graphics.height, 10f)
        renderer.shapes.rectLine(part1.x - renderDiffX + Gdx.graphics.width, part1.y + renderDiffY + Gdx.graphics.height, part2.x - renderDiffX + Gdx.graphics.width, part2.y - renderDiffY + Gdx.graphics.height, 10f)
    }

    override fun dispose() {
        EntitySystem.dispose(this)
    }
}