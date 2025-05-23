package com.example.coincapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coincapapp.navigation.BottomNavigationItem
import com.example.coincapapp.ui.theme.CoinCapAppTheme
import com.example.coincapapp.views.AssetsList
import com.example.coincapapp.views.BottomTabBar
import com.example.coincapapp.views.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinCapAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomTabBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavigationItem.Home.route
                        ) {
                            composable(BottomNavigationItem.Home.route) {
                                AssetsList()
                            }
                            composable(BottomNavigationItem.Settings.route) {
                                SettingsScreen() // Tu login con Firebase
                            }
                        }
                    }
                }
            }
        }
    }
}

