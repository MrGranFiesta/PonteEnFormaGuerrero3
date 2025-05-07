package com.mrgranfiesta.ponteenformaguerrero3.domain.utils

import org.junit.Test
import org.junit.Assert.*

class RegexExpresionTest {
    @Test
    fun testNoDigits() {
        // Casos válidos
        assertEquals("", "abc".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        assertEquals("", "abc-xyz".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        assertEquals("", " !@#".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        assertEquals("", "".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))

        // Casos inválidos
        assertEquals("123", "abc123".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        assertEquals("123", "123".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
        assertEquals("123", "a1b2c3".replace(RegexExpresion.NO_DIGITS_REMPLACE, ""))
    }

    @Test
    fun testNoDigitsDecimal() {
        // Casos válidos
        assertEquals("","abc".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals("", "abc-xyz".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals("", " !@#".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals("", "".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))

        // Casos inválidos
        assertEquals("123", "abc123".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals("123", "123".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals("123.45", "123.45".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
        assertEquals(".", "abc.xyz".replace(RegexExpresion.NO_DIGITS_DECIMAL_REMPLACE, ""))
    }

    @Test
    fun testIsDigits() {
        // Casos válidos
        assertTrue("123".matches(RegexExpresion.IS_DIGITS))
        assertTrue("0".matches(RegexExpresion.IS_DIGITS))
        assertTrue("9876543210".matches(RegexExpresion.IS_DIGITS))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.IS_DIGITS))
        assertFalse("abc".matches(RegexExpresion.IS_DIGITS))
        assertFalse("123abc".matches(RegexExpresion.IS_DIGITS))
        assertFalse("123.45".matches(RegexExpresion.IS_DIGITS))
        assertFalse("123 456".matches(RegexExpresion.IS_DIGITS))
    }

    @Test
    fun testIsDigitsDouble() {
        // Casos válidos (formato de número decimal)
        assertTrue("123.45".matches(RegexExpresion.IS_DIGITS_DOUBLE))
        assertTrue("0.0".matches(RegexExpresion.IS_DIGITS_DOUBLE))
        assertTrue("9876.54321".matches(RegexExpresion.IS_DIGITS_DOUBLE))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.IS_DIGITS_DOUBLE))
        // Sin punto decimal
        assertFalse("123".matches(RegexExpresion.IS_DIGITS_DOUBLE)) // Sin punto decimal
        assertFalse(".45".matches(RegexExpresion.IS_DIGITS_DOUBLE)) // Sin dígitos antes del punto
        assertFalse("123.".matches(RegexExpresion.IS_DIGITS_DOUBLE)) // Sin dígitos después del punto
        assertFalse("123.45.67".matches(RegexExpresion.IS_DIGITS_DOUBLE)) // Múltiples puntos
        assertFalse("abc.xyz".matches(RegexExpresion.IS_DIGITS_DOUBLE))
    }

    @Test
    fun testSecuredRe() {
        // Casos válidos
        assertTrue("abc123".matches(RegexExpresion.SECURED_RE))
        assertTrue("ABC123".matches(RegexExpresion.SECURED_RE))
        assertTrue("abc-123_xyz".matches(RegexExpresion.SECURED_RE))
        assertTrue("abc 123".matches(RegexExpresion.SECURED_RE))
        assertTrue("abc|?*+123".matches(RegexExpresion.SECURED_RE))
        assertTrue("ñÑ".matches(RegexExpresion.SECURED_RE))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.SECURED_RE))
        assertFalse("abc@123".matches(RegexExpresion.SECURED_RE)) // @ no permitido
        assertFalse("abc#123".matches(RegexExpresion.SECURED_RE)) // # no permitido
        assertFalse("abc!123".matches(RegexExpresion.SECURED_RE)) // ! no permitido
        assertFalse("abc$123".matches(RegexExpresion.SECURED_RE)) // $ no permitido
        assertFalse("abc\"123".matches(RegexExpresion.SECURED_RE)) // " no permitido
        assertFalse("abc\'123".matches(RegexExpresion.SECURED_RE)) // ' no permitido
        assertFalse("abc/123".matches(RegexExpresion.SECURED_RE)) // / no permitido
        assertFalse("abc\\123".matches(RegexExpresion.SECURED_RE)) // \ no permitido
    }

    @Test
    fun testSecuredSql() {
        // Casos válidos
        assertTrue("abc123".matches(RegexExpresion.SECURED_SQL))
        assertTrue("ABC123".matches(RegexExpresion.SECURED_SQL))
        assertTrue("abc-123_xyz".matches(RegexExpresion.SECURED_SQL))
        assertTrue("abc 123".matches(RegexExpresion.SECURED_SQL))
        assertTrue("ñÑ".matches(RegexExpresion.SECURED_SQL))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.SECURED_SQL))
        assertFalse("abc|123".matches(RegexExpresion.SECURED_SQL)) // | no permitido
        assertFalse("abc?123".matches(RegexExpresion.SECURED_SQL)) // ? no permitido
        assertFalse("abc*123".matches(RegexExpresion.SECURED_SQL)) // * no permitido
        assertFalse("abc+123".matches(RegexExpresion.SECURED_SQL)) // + no permitido
        assertFalse("abc@123".matches(RegexExpresion.SECURED_SQL)) // @ no permitido
        assertFalse("abc\"123".matches(RegexExpresion.SECURED_SQL)) // " no permitido
        assertFalse("abc\'123".matches(RegexExpresion.SECURED_SQL)) // ' no permitido
        assertFalse("abc/123".matches(RegexExpresion.SECURED_SQL)) // / no permitido
        assertFalse("abc\\123".matches(RegexExpresion.SECURED_SQL)) // \ no permitido
    }

    @Test
    fun testSecuredSqlEmail() {
        // Casos válidos (alfanumérico con caracteres de email)
        assertTrue("abc123".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertTrue("ABC123".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertTrue("abc-123_xyz".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertTrue("abc 123".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertTrue("user@example.com".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertTrue("ñÑ".matches(RegexExpresion.SECURED_SQL_EMAIL))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.SECURED_SQL_EMAIL))
        assertFalse("abc|123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // | no permitido
        assertFalse("abc?123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // ? no permitido
        assertFalse("abc*123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // * no permitido
        assertFalse("abc+123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // + no permitido
        assertFalse("abc\"123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // " no permitido
        assertFalse("abc\'123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // ' no permitido
        assertFalse("abc/123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // / no permitido
        assertFalse("abc\\123".matches(RegexExpresion.SECURED_SQL_EMAIL)) // \ no permitido
    }

    @Test
    fun testEmailFormat() {
        // Casos válidos (formato de email correcto)
        assertTrue("user@example.com".matches(RegexExpresion.EMAIL_FORMAT))
        assertTrue("user.name@example.com".matches(RegexExpresion.EMAIL_FORMAT))
        assertTrue("user-name@example.co.uk".matches(RegexExpresion.EMAIL_FORMAT))
        assertTrue("user_name@example-domain.com".matches(RegexExpresion.EMAIL_FORMAT))
        assertTrue("user+tag@example.com".matches(RegexExpresion.EMAIL_FORMAT))
        assertTrue("123@example.com".matches(RegexExpresion.EMAIL_FORMAT))

        // Casos inválidos
        assertFalse("".matches(RegexExpresion.EMAIL_FORMAT))
        assertFalse("userexample.com".matches(RegexExpresion.EMAIL_FORMAT)) // Sin @
        assertFalse("user@".matches(RegexExpresion.EMAIL_FORMAT)) // Sin dominio
        assertFalse("@example.com".matches(RegexExpresion.EMAIL_FORMAT)) // Sin nombre de usuario
        assertFalse("user@example".matches(RegexExpresion.EMAIL_FORMAT)) // Sin TLD
        assertFalse("user@example.c".matches(RegexExpresion.EMAIL_FORMAT)) // TLD demasiado corto
        assertFalse("user name@example.com".matches(RegexExpresion.EMAIL_FORMAT)) // Espacio en nombre de usuario
    }

    @Test
    fun testChatPermitPassword() {
        // Casos válidos
        assertFalse("password123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("PASSWORD123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("pass-word_123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("pass word 123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("password123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("pass!word123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("pass#word123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("ñÑ".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("abc/123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("abc$123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))
        assertFalse("abc/123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD))

        // Casos inválidos
        assertTrue("abc\"123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // " no permitido
        assertTrue("abc\'123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // ' no permitido
        assertTrue("abc\\123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // \ no permitido
        assertTrue("abc<123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // < no permitido
        assertTrue("abc>123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // > no permitido
        assertTrue("abc\uD83D\uDCA5123".contains(RegexExpresion.CHAT_NOT_PERMIT_PASSWORD)) // emoticono no permitido
    }
}