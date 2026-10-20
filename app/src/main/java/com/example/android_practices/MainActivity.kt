package com.example.android_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.android_practices.di.AppContainer
import com.example.android_practices.ui.factory.AppViewModelFactory
import com.example.android_practices.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(applicationContext)
        val factory = AppViewModelFactory(appContainer)

        setContent {
            MaterialTheme {
                AppNavigation(factory)
            }
        }
    }
}