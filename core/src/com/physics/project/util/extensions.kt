package com.physics.project.util

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.physics.project.Color
import com.physics.project.entities.Entity

fun ShapeRenderer.setColor(color: Color) = this.setColor(color.red, color.green, color.blue, 1f)

fun ShapeRenderer.render(entity: Entity) = entity.render(this)