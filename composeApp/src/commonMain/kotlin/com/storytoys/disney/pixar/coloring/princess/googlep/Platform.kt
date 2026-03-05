package com.storytoys.disney.pixar.coloring.princess.googlep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform