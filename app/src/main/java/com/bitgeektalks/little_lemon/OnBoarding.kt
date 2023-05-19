package com.bitgeektalks.little_lemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitgeektalks.little_lemon.ui.theme.Little_lemonTheme
import com.bitgeektalks.little_lemon.ui.theme.Primary1




@Composable
fun headingText() {
    Surface(
        color = Primary1,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                color = Color.White,
                text = "Let's get to know you",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp),
                style = TextStyle(fontSize = 20.sp)
            )
        }
    }
}

@Composable
fun formHeading(){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.15f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Personal Information",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineTextFieldSample(label:String): MutableState<String> {
    val text = remember { mutableStateOf("") }
//    Text(
//        text=label,
//        style = TextStyle (fontSize = 12.sp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//            .padding(8.dp)
//    )
    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(label) },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(8.dp)
    )
    return text
}

@Composable
fun submitButton(
    firstnameState: MutableState<String>,
    lastNameState: MutableState<String>,
    emailState: MutableState<String>,
    navController: NavController,
    context: Context,) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(vertical = 34.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                // your onClick code here

                val message = "First Name: ${firstnameState.value}\n" +
                        "Last Name: ${lastNameState.value}\n" +
                        "Email ID: ${emailState.value}"


                val valid =validation(firstnameState,lastNameState,emailState, context = context)
                // User data is not stored, store the data and then navigate to the Onboarding screen
                if(valid){
                    toastdata(context,message)
                    storeUserData(firstnameState.value,lastNameState.value,emailState.value, context = context)
                    navController.navigate(homeRoute.route)
                }

            }) {
            Text(text = "Register")
        }
    }
}
fun isEmailValid(email: String): Boolean {
    val pattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return pattern.matches(email)
}

fun validation(
    firstnameState: MutableState<String>,
    lastNameState: MutableState<String>,
    emailState: MutableState<String>,
    context: Context
):Boolean {
    if(firstnameState.value.isBlank()
        ||lastNameState.value.isBlank()
        ||emailState.value.isBlank()){
        toastdata(context,"All three fields need to be filed")
        return false
    }else if(!isEmailValid(emailState.value)){
        toastdata(context,"Email is not valid")
        return false
    }else{
        return true
    }
}

fun toastdata(context: Context,message:String){
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun storeUserData(firstName: String, lastName: String, email: String,context: Context) {
    val sharedPrefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPrefs.edit()

    editor.putString("firstName", firstName)
    editor.putString("lastName", lastName)
    editor.putString("email", email)

    editor.apply()
}
@Composable
fun Onboarding(navController: NavController,context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.06f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        headingText()
        formHeading()

        val firstnameState = OutLineTextFieldSample("First Name")
        val lastNameState = OutLineTextFieldSample("Last Name")
        val emailState = OutLineTextFieldSample("Email Id")
        submitButton(firstnameState,lastNameState,emailState,navController,context)

    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview(context: Context = LocalContext.current) {
    Little_lemonTheme {
        Onboarding(navController = rememberNavController(), context = context)
    }
}