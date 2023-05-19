package com.bitgeektalks.little_lemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitgeektalks.little_lemon.ui.theme.HighLight1
import com.bitgeektalks.little_lemon.ui.theme.Highlight2
import com.bitgeektalks.little_lemon.ui.theme.Little_lemonTheme
import com.bitgeektalks.little_lemon.ui.theme.Primary1
import com.bitgeektalks.little_lemon.ui.theme.Primary2
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Composable
fun headerSection(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .size(40.dp)
        )
        Surface(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 16.dp)
            .clickable {
                navController.navigate("profile")
            },
            shape = RoundedCornerShape(65.dp),

        ){
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
@Composable
fun InfoSection(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.74f)
        .padding(16.dp)
        ) {
        Column() {
            Text(
                text = "Little Lemon",
                style = TextStyle(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary2
                )
            )
            Text(
                text = "Chicago",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HighLight1
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = HighLight1
                ),
                modifier = Modifier.fillMaxWidth(0.58f)
            )
        }
        Surface(
            modifier = Modifier
                .width(147.dp)
                .height(147.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "hero_image",
                contentScale = ContentScale.Crop, // Added contentScale parameter
                modifier = Modifier
                    .fillMaxSize() // Adjusted modifier to fill the available size
            )
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun heroSection(onSearchQueryChanged: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .background(color = Primary1)
    ) {
        Column(modifier = Modifier.fillMaxHeight()){
            InfoSection()

            // Add the TextField as a search bar

            OutlinedTextField(
                value = searchQuery,
                leadingIcon = {
                    Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "searchIcon"
                        )
                    },
                onValueChange = { query ->
                        searchQuery = query
                        onSearchQueryChanged(query)
                    },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        color = HighLight1,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp))
                ,
                placeholder = { Text(text = "Enter Search Phrase") },
            )



        }
    }
}
@Composable
fun MenuItems(menuItems: List<MenuEntity>,context: Context) {
    Column {
        // Iterate over the menu items and display each item
        for (menuItem in menuItems) {
            MenuItem(menuItem,context)
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuEntity,context:Context) {
    val imageName = menuItem.title.lowercase().replace(" ","_")// Image name retrieved from the database // Replace with the actual image name from the database

    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
    val imageDrawable = if (imageResId != 0) {
        painterResource(id = imageResId)
    } else {
        // Handle the case where the image resource does not exist
        painterResource(id = R.drawable.hero_image) // Replace with the default image resource
    }

    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
       Column(
           modifier = Modifier.padding(16.dp)
       ) {
           Text(text = menuItem.title,
               style = TextStyle(
                   fontSize = 18.sp,
                   fontWeight = FontWeight.W600,
                   color = Highlight2))
           Spacer(modifier = Modifier.height(5.dp))
           Text(text = menuItem.description,
               modifier = Modifier.fillMaxWidth(0.7f),
               style = TextStyle(
                   fontSize = 15.sp,
                   color = Primary1
               ),
               maxLines = 2,
               overflow = TextOverflow.Ellipsis
           )
           Spacer(modifier = Modifier.height(5.dp))
           Text(text = "$${menuItem.price}",
               style = TextStyle(
                   color = Primary1,
                   fontSize = 16.sp,
                   fontWeight = FontWeight.SemiBold
               ))
       }
//        GlideImage(
//            model = menuItem.image,
//            contentDescription = menuItem.title, // Provide a suitable description if needed
//            modifier = Modifier.size(128.dp), // Adjust the size as per your requirement
//            contentScale = ContentScale.Crop // Adjust the content scale if needed
//        )
        Surface(
            modifier = Modifier
                .width(110.dp)
                .height(110.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 8.dp),
        ) {
            Image(
                painter = imageDrawable,
                contentDescription = "hero_image",
                contentScale = ContentScale.Crop, // Added contentScale parameter
                modifier = Modifier
                    .fillMaxSize() // Adjusted modifier to fill the available size
            )
        }
    }
    Divider( modifier = Modifier
        .padding(start = 16.dp, end = 16.dp)
        .height(1.dp)
        .background(color = Primary1))
}

@Composable
fun categoryButton(category:String, onClick: () -> Unit){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    ) {
        Text(text = category, style = TextStyle(color = Primary1, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun MenuBreakdown(menuItems: List<MenuEntity>, context: Context) {

    var categories = mutableSetOf<String>()
    for (menuItem in menuItems) {
        categories.add(menuItem.category)
    }
    println(categories)
    val filteredMenuItems = remember { mutableStateOf(menuItems) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold) )
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            for (category in categories){
                categoryButton(category = category) {
                    filteredMenuItems.value = menuItems.filter {menuItem -> menuItem.category.contains(category, ignoreCase = true) }
                }
            }
        }
        Divider( modifier = Modifier
            .padding(top=8.dp)
            .height(1.5.dp)
            .background(color = Primary1))
    }
    MenuItems(menuItems = filteredMenuItems.value, context)
}

@Composable
fun home(navController: NavController, value: List<MenuEntity>,context: Context){
//    Surface(modifier = Modifier.fillMaxSize()) {
//        Text(text = "HomeScreen",modifier = Modifier.clickable {
//            navController.navigate(profileRoute.route)
//        })
//    }
    val searchQuery = remember { mutableStateOf("") }

    val filteredMenuItems = remember(value, searchQuery.value) {
        derivedStateOf {
            if (searchQuery.value.isBlank()) {
                value
            } else {
                value.filter { menuItem ->
                    menuItem.title.contains(searchQuery.value, ignoreCase = true)
                }
            }
        }
    }

    Little_lemonTheme() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            headerSection(navController)
            Spacer(modifier = Modifier.height(8.dp))
            heroSection(onSearchQueryChanged = { query ->
                searchQuery.value = query
            })
            MenuBreakdown(
                menuItems = value,
                context,

            )
            MenuItems(menuItems = filteredMenuItems.value, context)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(context: Context = LocalContext.current){
    Little_lemonTheme {
        var value = emptyList<MenuEntity>()
        home(rememberNavController(), value,context)
    }
}
