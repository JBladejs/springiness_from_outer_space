package com.physics.project.util

import com.badlogic.gdx.math.MathUtils.PI

fun degreesToRadians(degrees: Float): Float = degrees * 0.01745329251f // degrees * PI/180
fun radiansToDegrees(radians: Float): Float = radians * 57.2957795131f // radians * 180/PI

fun floorMod(one: Float,two: Int): Float = ((one % two) + two) % two