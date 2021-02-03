package com.example.peselchecker.pesel

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PeselValidator {

    private val WEIGHTS = listOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3)
    private val CONTROL_DIGIT_INDEX = 10
    private val CORRECT_PESEL_LENGTH = 11

    private fun lengthAndNumberCorrectness(pesel: String): Boolean {
        return pesel.toLongOrNull() != null && pesel.length == CORRECT_PESEL_LENGTH
    }

    private fun centuryFromMonth(month: Int): String {
        return when {
            month < 20 -> "19"
            month < 40 -> "20"
            month < 60 -> "21"
            month < 80 -> "22"
            else -> "18"
        }
    }

    private fun sex(pesel: String): Sex {
        return if (pesel[9].toInt() % 2 == 0) Sex.FEMALE
        else Sex.MALE
    }

    private fun dateOfBirth(pesel: String): String {
        val year = pesel.subSequence(0, 2).toString()
        val month = pesel.subSequence(2, 4).toString().toInt() % 20
        val day = pesel.subSequence(4, 6).toString()

        val century = centuryFromMonth(pesel.subSequence(2, 4).toString().toInt())

        val dateOfBirth = "${century}${year}-${month.toString().padStart(2, '0')}-${day}"

        return try {
            LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            dateOfBirth
        } catch (e: IllegalArgumentException) {
            "Invalid date of birth in PESEL"
        }
    }

    private fun calculateCheckNumber(pesel: String): Int {
        var sum = 0
        for (i in WEIGHTS.indices) {
            sum += (pesel[i].toString().toInt() * WEIGHTS[i])
        }
        return if ((10 - sum % 10) == 10) 0
        else 10 - (sum % 10)
    }

    private fun checkChecksum(pesel: String): Boolean {
        return (pesel[CONTROL_DIGIT_INDEX].toString().toInt() == calculateCheckNumber(pesel))
    }

    fun validate(pesel: String): PeselValidResult? {
        return if (lengthAndNumberCorrectness(pesel)) {
            PeselValidResult(
                dateOfBirth(pesel),
                sex(pesel),
                checkChecksum(pesel)
            )
        } else
            null
    }


}