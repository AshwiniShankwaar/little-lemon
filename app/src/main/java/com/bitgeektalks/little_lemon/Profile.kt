package com.bitgeektalks.little_lemon

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitgeektalks.little_lemon.ui.theme.Little_lemonTheme

@Composable
fun Header(){
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.06f)
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profileData(data: String,label:String) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = label,modifier = Modifier.padding(horizontal = 16.dp), style = TextStyle(fontSize = 13.sp) )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 0.05.dp, color = Color.Black)
        ) {

            Text(
                text = data,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}

@Composable
fun form(context: Context) {
    val sharedPrefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val firstName = sharedPrefs.getString("firstName", null)
    val lastName = sharedPrefs.getString("lastName", null)
    val email = sharedPrefs.getString("email", null)
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),) {
        Spacer(modifier = Modifier.fillMaxHeight(0.09f))
        Text(text = "Personal information",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp),
        modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
        profileData(firstName.orEmpty(),"First Name")
        profileData(lastName.orEmpty(),"Last Name")
        profileData(email.orEmpty(),"Email id")
    }

}
@Composable
fun submitButton(context: Context,navController: NavController) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            // your onClick code here
            clearSharedPreferences(context = context)
            navController.navigate(OnboardingRoute.route)
        }
    ) {
        Text(text = "Logout")
    }
}
fun clearSharedPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}

@Composable
fun profile(navController: NavController,context: Context) {
    Little_lemonTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Header()
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                form(context)
            }
            submitButton(context = context,navController)
        }
    }
}



@Preview
@Composable
fun ProfilePreview() {
    val context = LocalContext.current
    Little_lemonTheme {
        profile(rememberNavController(),context)
    }
}
