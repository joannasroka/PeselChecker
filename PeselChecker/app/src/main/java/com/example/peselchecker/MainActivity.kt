// Joanna Sroka 246756
// Testowane na Xiaomi Redmi 6

package com.example.peselchecker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.peselchecker.pesel.PeselValidator
import com.example.peselchecker.pesel.PeselViewModel


class MainActivity : AppCompatActivity() {
    private val peselViewModel by viewModels<PeselViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeselChecker(peselViewModel)

        }
    }

}

@Composable
fun PeselChecker(peselViewModel: PeselViewModel) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Surface() {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = peselViewModel.pesel.value,
                    onValueChange = { input: TextFieldValue ->
                        peselViewModel.pesel.value = input
                        onPeselValidation(
                            input.text, peselViewModel.isValid, peselViewModel.birthDate,
                            peselViewModel.sex, peselViewModel.checksum
                        )
                    },
                    label = { Text(text = "Write PESEL") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Text(
                    text = peselViewModel.isValid.value,
                    style = typography.subtitle1,
                    modifier = Modifier.padding(0.dp, 16.dp)
                )

                Column {
                    Text("Checksum validation: ", style = typography.subtitle1 )
                    Text(peselViewModel.checksum.value, style = typography.subtitle2 )
                }

                Column {
                    Text("Date of birth: ", style = typography.subtitle1 )
                    Text(peselViewModel.birthDate.value, style = typography.subtitle2 )
                }

                Column {
                    Text("Sex: ", style = typography.subtitle1 )
                    Text(peselViewModel.sex.value, style = typography.subtitle2 )
                }

            }
        }
    }
}

fun onPeselValidation(pesel: String,
                      isValid: MutableState<String>,
                      birthDate: MutableState<String>,
                      sex: MutableState<String>,
                      checksum: MutableState<String>) {

    val peselValidator = PeselValidator()
    val peselInfo = peselValidator.validate(pesel)

    if (peselInfo != null) {
        isValid.value = "The length of PESEL number is valid"
        birthDate.value = peselInfo.dateOfBirth
        sex.value = peselInfo.sex.toString()
        checksum.value = peselInfo.checkSumCorrectness.toString()
    } else {
        isValid.value = "PESEL number is invalid. PESEL should be 11 digits"
        birthDate.value = ""
        sex.value = ""
        checksum.value = ""
    }
}
