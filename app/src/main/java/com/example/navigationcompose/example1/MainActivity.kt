package com.example.navigationcompose.example1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationcompose.ui.theme.NavigationComposeTheme




sealed class Destination(val route:String) {
  object Home: Destination("home")
  object Profile: Destination("profile")
  object List: Destination("list")
  object Detail: Destination("detail/{elementId}/{age}"){
      fun createRoute(elementId:Int , age:Int) = "detail/$elementId/$age"
  }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController){

    val ctx = LocalContext.current
                                            // startDestination = "home"
    NavHost(navController = navController, startDestination = Destination.Home.route ) {
        composable(Destination.Home.route) { HomeScreen(navController) }
        composable(Destination.Profile.route) { ProfileScreen() }
        composable(Destination.List.route) { ListScreen(navController) }
        composable(Destination.Detail.route) { navBackStackEntry ->
            val elementId = navBackStackEntry.arguments?.getString("elementId")
            val age = navBackStackEntry.arguments?.getString("age")
            var ss = age
            if(elementId == null  || age == null){
                Toast.makeText(ctx,"ElementId or Age required",Toast.LENGTH_LONG).show()
            }else{
               DetailScreen(elementId = elementId.toInt() , age = age.toInt())
            }
        }
    }
}

