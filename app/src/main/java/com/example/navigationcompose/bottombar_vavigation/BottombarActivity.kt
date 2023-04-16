package com.example.navigationcompose.bottombar_vavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationcompose.R
import com.example.navigationcompose.ui.theme.NavigationComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BottombarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "Detail" -> false // on this screen bottom bar should be hidden
//        "RouteOfScreenB" -> false // here too
        else -> true // in all other cases show bottom bar
    }


    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current.applicationContext

    val scope = rememberCoroutineScope()

    Scaffold(


        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Drawer Sample")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                    ) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = ""
                        )
                    }
                })
        },
        drawerGesturesEnabled = true,
        drawerContent = {
            DrawerView(onEventCallback = { route ->

                navController.navigate(route)
                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        },


        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(navController)
        },


        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                /* Add code later */
                NavigationAppHost(navController = navController)
            }
        },
        backgroundColor = colorResource(R.color.purple_700) // Set background color to avoid the white flashing when you switch between screens
    )
}


@Composable
fun DrawerView(
    onEventCallback: (route: String) -> Unit
) {

    LazyColumn {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
                    .padding(all = 20.dp)
                    .clickable {
                        onEventCallback(Destination.Home.route)
                    }
            ) {
                Text("Home", fontSize = 25.sp, color = Color.White)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Blue)
                    .padding(all = 20.dp)
                    .clickable {
                        onEventCallback(Destination.Music.route)
                    }
            ) {
                Text("Music", fontSize = 25.sp, color = Color.Red)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Green)
                    .padding(all = 20.dp)
                    .clickable {
                        onEventCallback(Destination.Profile.route)
                    }
            ) {
                Text("Profile", fontSize = 25.sp, color = Color.Red)
            }
        }


        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
                    .padding(all = 20.dp)
                    .clickable {
                        onEventCallback(Destination.Detail.route)
                    }
            ) {
                Text("Detailed", fontSize = 25.sp, color = Color.Red)
            }
        }


    }

}


/**************************************** bottom bar *************************************/

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Destination.Home,
        Destination.Music,
        Destination.Profile
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_700),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon!!), contentDescription = item.title) },
                label = { Text(text = item.title!!) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}


sealed class Destination(var route: String, var icon: Int?, var title: String?) {
    object Home : Destination("home", R.drawable.ic_home, "Home")
    object Music : Destination("music", R.drawable.ic_music, "Music")
    object Profile : Destination("profile", R.drawable.ic_profile, "Profile")
    object Detail : Destination("Detail", null, null)
}


@Composable
fun NavigationAppHost(navController: NavHostController) {
    NavHost(navController, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }
        composable(Destination.Music.route) {
            MusicScreen(navController)
        }
        composable(Destination.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Destination.Detail.route) {
            DetailedScreen(navController)
        }
    }
}


//sealed class Destination(val route: String){
//    object HomeScreen: Destination("home_screen")
//    object SearchScreen: Destination("search_screen")
//    object ProfileScreen: Destination("profile_screen")
//}

//@Composable
//fun SecondExampleNavigationAppHost(navController: NavHostController){
//
//    val ctx = LocalContext.current
//    // startDestination = "home"
//    NavHost(navController = navController, startDestination = Destination.HomeScreen.route ) {
//        composable(Destination.HomeScreen.route) { HomeScreen(navController) }
//        composable(Destination.MusicScreen.route) { MusicScreen(navController) }
//        composable(Destination.ProfileScreen.route) { ProfileScreen(navController) }
//
//    }
//}
