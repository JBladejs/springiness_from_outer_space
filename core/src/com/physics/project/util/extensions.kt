package com.physics.project.util

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.graphics.Color

fun ShapeRenderer.setColor(color: Color) = this.setColor(color.red, color.green, color.blue, 1f)

infix fun <T: Number> Float.fMod(num: T): Float {
    val numF = num.toFloat()
    return ((this % numF) + numF) % numF
}