package com.physics.project.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.physics.project.SpringinessGame

object DesktopLauncher {
    @JvmStatic
    fun main(arag: Array<String>) {
        val config = LwjglApplicationConfiguration()
        with(config) {
            title = "Springiness From Outer Space"
            width = 900
            height = 900
            resizable = false
//            vSyncEnabled = false
//            foregroundFPS = 0
//            backgroundFPS = 0
            LwjglApplication(SpringinessGame(), config)
        }
    }
}