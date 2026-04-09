package org.cherrypick.picker.shared.time

import java.time.Instant

fun interface ClockProvider {
    fun now(): Instant

    companion object {
        val system = ClockProvider { Instant.now() }
    }
}
