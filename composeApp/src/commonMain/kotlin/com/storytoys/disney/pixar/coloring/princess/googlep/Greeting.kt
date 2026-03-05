package com.storytoys.disney.pixar.coloring.princess.googlep

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}