package org.paystell.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform