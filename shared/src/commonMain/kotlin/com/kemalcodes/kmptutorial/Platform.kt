package com.kemalcodes.kmptutorial

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform