package com.example.peselchecker.pesel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.peselchecker.R

class PeselViewModel : ViewModel() {
    val pesel = mutableStateOf(TextFieldValue())
    val isValid = mutableStateOf("No input")
    val birthDate = mutableStateOf("")
    val sex = mutableStateOf("")
    val checksum = mutableStateOf("")
}