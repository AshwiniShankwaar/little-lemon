package com.bitgeektalks.little_lemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitgeektalks.little_lemon.ui.theme.Little_lemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("Text","Plain"))
        }
    }
    private suspend fun getMenu():MenuNetwork{
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "raw.githubusercontent.com"
                encodedPath = "/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
            }
        }

        val json = response.bodyAsText()
        println("Response JSON: $json")
        return Json.decodeFromString(json)
    }
    private val menuItemsState = mutableStateOf<List<MenuEntity>>(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this code is for getting the data from the network and store in the room
//        menuDatabase = MenuDatabase.getInstance(applicationContext)
//        menuDao = menuDatabase.menuDao()
//
//        GlobalScope.launch(Dispatchers.IO) {
//            // Fetch menu data from network
//            val menuNetwork = getMenu()
//
//            // Map network data models to Room entities
//            val menuEntities = menuNetwork.menu.map { menuItem ->
//                MenuEntity(
//                    id = menuItem.id,
//                    title = menuItem.title,
//                    description = menuItem.description,
//                    price = menuItem.price,
//                    image = menuItem.image
//                )
//            }
//
//            // Save menu entities to the database
//            menuDao.insertMenu(menuEntities)
//        }
        val jsonData = """{
  "menu": [
    {
      "id": 1,
      "title": "Greek Salad",
      "description": "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
      "category": "starters"
    },
    {
      "id": 2,
      "title": "Lemon Desert",
      "description": "Traditional homemade Italian Lemon Ricotta Cake.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true",
      "category": "desserts"
    },
    {
      "id": 3,
      "title": "Grilled Fish",
      "description": "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true",
      "category": "mains"
    },
    {
      "id": 4,
      "title": "Pasta",
      "description": "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chili, garlic, basil & salted ricotta cheese.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true",
      "category": "mains"
    },
    {
      "id": 5,
      "title": "Bruschetta",
      "description": "Oven-baked bruschetta stuffed with tomatoes and herbs.",
      "price": "10",
      "image": "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/bruschetta.jpg?raw=true",
      "category": "starters"
    }
  ]
} 
 
        """

        val menuNetwork = Json.decodeFromString<MenuNetwork>(jsonData)

        val menuEntities = menuNetwork.menu.map { menuItem ->
            MenuEntity(
                id = menuItem.id,
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image,
                category = menuItem.category
            )
        }

        GlobalScope.launch(Dispatchers.IO) {
            val menuDao = MenuDatabase.getInstance(applicationContext).menuDao()
            menuDao.insertMenu(menuEntities)
        }

        setContent {
            val menuDao = MenuDatabase.getInstance(applicationContext).menuDao()

            LaunchedEffect(Unit) {
                try {
                    withContext(Dispatchers.IO) {
                        val menuItems = menuDao.getMenu()
                        // Update the menuItemsState with the retrieved menu items
                        menuItemsState.value = menuItems
                    }
                } catch (e: Exception) {
                    // Handle any exceptions that occur during the database retrieval
                    // You can display an error message or take appropriate action
                }
            }

            Little_lemonTheme {
                // A surface container using the 'background' color from the theme

                MyNavigation(context = this@MainActivity,menuItemsState.value)

            }
        }


    }

}
fun isUserDataStored(context: Context): Boolean {
    val sharedPrefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val firstName = sharedPrefs.getString("firstName", null)
    val lastName = sharedPrefs.getString("lastName", null)
    val email = sharedPrefs.getString("email", null)

    // Check if all values are present
    return !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty() && !email.isNullOrEmpty()
}
@Composable
fun MyNavigation(context: Context, value: List<MenuEntity>){
    val navController = rememberNavController()
    val startDestination = if (isUserDataStored(context = context)) {
        homeRoute.route
    } else {
        OnboardingRoute.route
    }

    NavHost(navController = navController,
    startDestination = startDestination){
        composable(OnboardingRoute.route){
            Onboarding(navController = navController,context = context)
        }
        composable(homeRoute.route){
            home(navController = navController,value,context = context)
        }
        composable(profileRoute.route){
            profile(navController = navController,context = context)
        }
    }
}
