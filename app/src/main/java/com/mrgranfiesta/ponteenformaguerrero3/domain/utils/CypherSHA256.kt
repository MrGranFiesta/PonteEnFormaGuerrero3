package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import java.security.MessageDigest

class CypherSHA256 {
    companion object {
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val hashedBytes = md.digest(bytes)
            return hashedBytes.joinToString("") { "%02x".format(it) }
        }
    }
}