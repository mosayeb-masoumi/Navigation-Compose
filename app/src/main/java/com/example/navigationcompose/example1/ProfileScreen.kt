package com.example.navigationcompose.example1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Blue), contentAlignment = Alignment.Center) {

        Text(text = "Profile Screen" , fontSize = 25.sp , color = Color.White)
    }
}