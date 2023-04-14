package com.example.navigationcompose.example1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun ListScreen(navController: NavHostController) {

    val elements = MutableList(20) { it }

    LazyColumn(modifier = Modifier.background(color = Color.LightGray)) {
        items(elements) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(50.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color = Color.White)
                    .clickable {
                      navController.navigate(Destination.Detail.createRoute(it))
                    }
            ) {
              Text(text = "Element $it" , fontSize = 15.sp)
            }
        }
    }
}

