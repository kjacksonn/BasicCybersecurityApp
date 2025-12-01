package com.kj.basiccybersecurityapp.core

import kotlin.math.ln

/**
 * This object scores how strong a password is.
 * Think of it like a teacher grading a password from 0 to 100.
 */
object PasswordStrength {

    // Super common bad password pieces.
    private val commonBases = setOf(
        "password", "qwerty", "abc123", "letmein", "welcome", "dragon", "iloveyou",
        "admin", "login", "football", "monkey", "starwars", "princess", "passw0rd"
    )

    // Simple keyboard patterns we want to avoid.
    private val keyboardRuns = listOf("qwerty", "asdf", "zxcv", "12345", "09876")

    /**
     * Result of checking a password.
     *
     * score        -> final grade from 0 to 100
     * label        -> text like "Weak" or "Strong"
     * feedback     -> list of tips to help fix it
     * entropyBits  -> math number that shows how random it is
     */
    data class Result(
        val score: Int,
        val label: String,
        val feedback: List<String>,
        val entropyBits: Double
    )

    /**
     * Main function: give it a password string, and it returns a Result.
     */
    fun evaluate(pw: String): Result {
        // If the box is empty, tell the user to type something.
        if (pw.isEmpty()) {
            return Result(
                score = 0,
                label = "Very Weak",
                feedback = listOf("Enter a password"),
                entropyBits = 0.0
            )
        }

        val length = pw.length
        val hasLower = pw.any { it.isLowerCase() }
        val hasUpper = pw.any { it.isUpperCase() }
        val hasDigit = pw.any { it.isDigit() }
        val hasSymbol = pw.any { !it.isLetterOrDigit() }

        // We estimate how big the "alphabet" is that the user is pulling from.
        // More types of characters = harder to guess.
        var charsetSize = 0
        if (hasLower) charsetSize += 26
        if (hasUpper) charsetSize += 26
        if (hasDigit) charsetSize += 10
        if (hasSymbol) charsetSize += 33

        // "entropy" = how random the password looks, in bits.
        // You can think of it like "randomness points from math".
        val entropy = if (charsetSize > 0) {
            length * (ln(charsetSize.toDouble()) / ln(2.0))
        } else {
            0.0
        }

        // We cap the math score at 60 so it doesn't blow up too high.
        val randomnessPointsFromMath = entropy.coerceIn(0.0, 60.0)

        // Extra bonus points for good behavior (mixed characters, longer length, etc.)
        var complexityBonus = 0.0
        if (hasLower && hasUpper) complexityBonus += 5      // both lower + upper
        if (hasDigit) complexityBonus += 5                  // has numbers
        if (hasSymbol) complexityBonus += 7                 // has symbols like !@#$
        if (length >= 12) complexityBonus += 3              // long enough

        // Take away points for bad stuff.
        if (hasLongRepeat(pw)) complexityBonus -= 8         // aaa or 111 repeated
        if (hasSimpleSequence(pw)) complexityBonus -= 8     // abc or 123
        if (looksLikeDate(pw)) complexityBonus -= 5         // looks like a date

        val lowerPw = pw.lowercase()
        val inCommonBase = commonBases.any { lowerPw.contains(it) }
        val inKeyboardRun = keyboardRuns.any { lowerPw.contains(it) }

        // Final score is math randomness + bonus points.
        var score = (randomnessPointsFromMath + complexityBonus)
            .toInt()
            .coerceIn(0, 100)

        val feedback = mutableListOf<String>()

        // If they used classic bad patterns, push score down and warn them.
        if (inCommonBase || inKeyboardRun) {
            score = minOf(score, 20)
            feedback += "Avoid common words or keyboard patterns like 'password' or 'qwerty'."
        }
        if (length < 12) feedback += "Use at least 12 characters."
        if (!hasUpper) feedback += "Add some UPPERCASE letters."
        if (!hasLower) feedback += "Add some lowercase letters."
        if (!hasDigit) feedback += "Add a few numbers."
        if (!hasSymbol) feedback += "Add symbols like !, ?, or #."
        if (hasLongRepeat(pw)) feedback += "Avoid repeating the same character many times."
        if (hasSimpleSequence(pw)) feedback += "Avoid simple sequences like abc or 123."
        if (looksLikeDate(pw)) feedback += "Don't use dates like birthdays."

        // Turn the numeric score into a label that makes sense to a student.
        val label = when (score) {
            in 0..19 -> "Very Weak"
            in 20..39 -> "Weak"
            in 40..59 -> "Fair"
            in 60..79 -> "Strong"
            else -> "Excellent"
        }

        return Result(
            score = score,
            label = label,
            feedback = feedback.distinct(),
            entropyBits = entropy
        )
    }

    // ----- Helper checks -----

    // True if any character repeats 3 or more times in the password (aaa, 111, etc.)
    private fun hasLongRepeat(pw: String): Boolean =
        pw.groupBy { it }.values.any { it.size >= 3 }

    // True if password contains simple abc or 123 style runs.
    private fun hasSimpleSequence(pw: String): Boolean {
        val s = pw.lowercase()
        val runs = listOf("abcdefghijklmnopqrstuvwxyz", "0123456789")
        return runs.any { run ->
            run.windowed(3).any { s.contains(it) }
        }
    }

    // Checks if the password looks like a date: 4, 6, or 8 digits.
    private fun looksLikeDate(pw: String): Boolean {
        val digits = pw.filter { it.isDigit() }
        return digits.length in setOf(4, 6, 8)
    }
}
