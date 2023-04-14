package com.example.navigationcompose.example1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(text = "Home Screen" , fontSize = 25.sp)
        Button(onClick = {
            navController.navigate(Destination.Profile.route)
        }) {
            Text(text = "to Profile Screen")
        }

        Button(onClick = {
            navController.navigate(Destination.List.route)
        }) {
            Text(text = "to List Screen")
        }
    }
}