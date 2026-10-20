package com.example.android_practices.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.android_practices.ui.factory.AppViewModelFactory

@Composable
fun SettingsScreen(
    navController: NavController,
    factory: AppViewModelFactory,
    viewModel: SettingsViewModel = viewModel(factory = factory)
) {
    val savedUserId by viewModel.userId.collectAsStateWithLifecycle()
    val savedLimit by viewModel.limit.collectAsStateWithLifecycle()

    var userIdText by remember { mutableStateOf("") }

    LaunchedEffect(savedUserId) {
        userIdText = savedUserId.toString()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Настройки фильтрации", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = userIdText,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    userIdText = it
                }
            },
            label = { Text("ID Пользователя (0 = Все)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Text("Количество постов: $savedLimit")
        Slider(
            value = savedLimit.toFloat(),
            onValueChange = { viewModel.onLimitChanged(it.toInt()) },
            valueRange = 5f..50f,
            steps = 8
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.reset()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Сбросить")
            }

            Button(
                onClick = {
                    val id = userIdText.toIntOrNull() ?: 0
                    viewModel.save(id, savedLimit)
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Готово")
            }
        }
    }
}