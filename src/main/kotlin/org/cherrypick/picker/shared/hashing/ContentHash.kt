package org.cherrypick.picker.shared.hashing

import java.security.MessageDigest

@JvmInline
value class ContentHash(
    val value: String,
) {
    companion object {
        fun of(content: String): ContentHash {
            val digest = MessageDigest.getInstance("SHA-256").digest(content.toByteArray())
            val hex = digest.joinToString("") { "%02x".format(it) }
            return ContentHash(hex)
        }
    }
}
