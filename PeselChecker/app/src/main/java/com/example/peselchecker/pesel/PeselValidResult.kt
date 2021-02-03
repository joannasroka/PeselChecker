package com.example.peselchecker.pesel

data class PeselValidResult (
    val dateOfBirth: String,
    val sex: Sex,
    val checkSumCorrectness: Boolean
)

enum class Sex {
    MALE,
    FEMALE
}