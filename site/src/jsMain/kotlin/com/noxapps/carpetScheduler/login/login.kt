package com.noxapps.carpetScheduler.login

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.login.components.styledInput
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.css.width
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.BoxScope
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.graphics.Image
import dev.gitlive.firebase.FirebaseApp
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun login(
    app:FirebaseApp,
    coroutineScope: CoroutineScope,
    loggedUser: MutableState<User>,
    navController: FauxNavController,
    viewModel: LoginViewModel = LoginViewModel(app, coroutineScope, loggedUser, navController)
) {
    var signUpState by remember { mutableStateOf(false) }
    val email = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }


    val firstName = remember{ mutableStateOf("") }
    val lastName = remember{ mutableStateOf("") }
    val phoneNumber = remember{ mutableStateOf("") }
    val confirmPassword = remember{ mutableStateOf("") }
    val linkingCode = remember{ mutableStateOf("") }

    val nameErrorState = remember{mutableStateOf(false)}
    val phoneErrorState = remember{mutableStateOf(false)}
    val passwordValidErrorState = remember{mutableStateOf(false)}
    val passwordMatchErrorState = remember{mutableStateOf(false)}
    val linkingCodeErrorState = remember{mutableStateOf(false)}
    val emailErrorState = remember{mutableStateOf(false)}
    val executeErrorState = remember{mutableStateOf(false)}


    val loginErrorStates = listOf(emailErrorState)
    val registerErrorStates = listOf(executeErrorState, nameErrorState, phoneErrorState, passwordValidErrorState,passwordMatchErrorState, linkingCodeErrorState, emailErrorState)

    val processingState = remember{mutableStateOf(false)}

    val loginErrorState = remember{mutableStateOf(false)}
    val registerErrorMessageState = remember{ mutableStateOf(false) }

    //var passwordErrorState = remember{ mutableStateOf(false) }
    val errorMessage = remember{ mutableStateOf("") }

    var actualWidth = 0


    Box(modifier = Modifier //conatiner
        .fillMaxWidth()
        .minHeight(100.percent)
        .position(Position.Absolute)
        .background(Color.argb(0.2f, 169, 169, 169))
    ) {
        Column(modifier = Modifier
            .align(Alignment.Center)
            .minWidth(30.percent)
            .border(
                width = 3.px,
                color = Colors.Black,
                style = LineStyle.Solid
            )
            .padding(10.px),
            ref = ref {
                actualWidth = it.offsetWidth
            }
        ) {

            Image(
                src = "resources/public/logo.png",
                description = "placeholderLogo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            if (!signUpState) {

                if(loginErrorState.value){
                    Column(modifier = Modifier
                        .background(Colors.PaleVioletRed)
                        .width(actualWidth.px)
                        .border(2.px, LineStyle.Solid, Colors.Red )
                        .padding(10.px)
                    ) {
                        H4(){
                            Text("Failed to log in. Error:")
                        }
                        Ul {
                            Li {
                                Text(errorMessage.value)
                            }
                        }
                    }

                }

                styledInput(
                    valueHolder = email,
                    placeholder = "Email",
                    processingState = processingState, 
                    errorState = emailErrorState
                ){}
                styledInput(
                    valueHolder = password,
                    inputType = InputType.Password,
                    placeholder = "Password",
                    processingState = processingState, 
                    errorState = passwordMatchErrorState
                ){
                    onKeyDown {
                        if(it.key == "Enter"){
                            passwordMatchErrorState.value = password.value.isEmpty()
                            emailErrorState.value = !email.value.isValidEmail()
                            if (!loginErrorStates.containsValue(true)) {
                                processingState.value=true
                                viewModel.login(
                                    email.value,
                                    password.value,
                                    processingState,
                                    loginErrorState,
                                    errorMessage
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        passwordValidErrorState.value = password.value.isEmpty()
                        emailErrorState.value = !email.value.isValidEmail()
                        loginErrorStates.forEach {
                            println(it)

                        }
                        if (!loginErrorStates.containsValue(true)) {
                            processingState.value=true
                            viewModel.login(
                                email.value,
                                password.value,
                                processingState,
                                loginErrorState,
                                errorMessage
                            )
                        }
                    },
                    enabled = !processingState.value,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px,0.px)
                ) {
                    if(!processingState.value)Text("Login")
                    else Text("Logging in...")
                }

            } else {       //sign up
                if(registerErrorMessageState.value){
                    Column(modifier = Modifier
                        .background(Colors.PaleVioletRed)
                        .width(actualWidth.px)
                        .border(2.px, LineStyle.Solid, Colors.Red )
                        .padding(10.px)
                    ) {
                        if (executeErrorState.value) {
                            H4() {
                                Text("Failed to log in. Error:")
                            }
                            Ul {
                                Li {
                                    Text(errorMessage.value)
                                }
                            }
                        }
                        else{
                            H4(){
                                Text("Please fix the following errors:")
                            }
                            Ul {
                                if (nameErrorState.value) Li { Text("Please enter your name.") }
                                if (phoneErrorState.value) Li { Text("Please enter your phone number.") }
                                if (emailErrorState.value) Li { Text("Please enter a valid email address.") }
                                if (passwordValidErrorState.value) Li { Text("Passwords must be 8 character, contain an uppercase letter, a lowercase letter and a digit.") }
                                if (passwordMatchErrorState.value) Li { Text("Passwords do not match.") }
                                if (linkingCodeErrorState.value) Li { Text("Invalid organisation linking code.") }
                            }
                        }
                    }

                }
                styledInput(
                    valueHolder = firstName,
                    placeholder = "First Name",
                    processingState = processingState, 
                    errorState = nameErrorState
                ){}
                styledInput(
                    valueHolder = lastName,
                    placeholder = "Last Name",
                    processingState = processingState, 
                    errorState = nameErrorState
                ){}
                styledInput(
                    valueHolder = phoneNumber,
                    placeholder = "Phone Number",
                    processingState = processingState, 
                    errorState = phoneErrorState
                ){}
                styledInput(
                    valueHolder = email,
                    placeholder = "Email",
                    processingState = processingState, 
                    errorState = emailErrorState
                ){}
                styledInput(
                    valueHolder = password,
                    inputType = InputType.Password,
                    placeholder = "Password",
                    processingState = processingState, 
                    errorState = mutableStateOf(passwordMatchErrorState.value || passwordValidErrorState.value)
                ){}
                styledInput(
                    valueHolder = confirmPassword,
                    inputType = InputType.Password,
                    placeholder = "Confirm Password",
                    processingState = processingState, 
                    errorState = passwordMatchErrorState
                ){
                    onKeyDown {}
                }
                styledInput(
                    valueHolder = linkingCode,
                    inputType = InputType.Password,
                    placeholder = "Linking Code",
                    processingState = processingState, 
                    errorState = linkingCodeErrorState
                ){
                    onKeyDown {
                        if(it.key == "Enter")  {
                            passwordValidErrorState.value = !password.value.isValidPassword()
                            passwordMatchErrorState.value = (password.value!=confirmPassword.value)||password.value.isEmpty()
                            linkingCodeErrorState.value = !viewModel.linkingCodes.contains(linkingCode.value)
                            emailErrorState.value = !email.value.isValidEmail()
                            nameErrorState.value = firstName.value.isEmpty()||lastName.value.isEmpty()
                            phoneErrorState.value = try{
                                phoneNumber.value.toLong()<10000000
                            } catch (e:Exception){
                                true
                            }
                            if(!registerErrorStates.containsValue(true)){
                                registerErrorMessageState.value = false
                                processingState.value=true
                                viewModel.register(
                                    email.value,
                                    password.value,
                                    firstName.value,
                                    lastName.value,
                                    phoneNumber.value,
                                    linkingCode.value,
                                    processingState,
                                    executeErrorState,
                                    errorMessage
                                )
                            }
                            else registerErrorMessageState.value = true
                        }
                    }
                }

                Button(
                    onClick = {
                        passwordValidErrorState.value = !password.value.isValidPassword()
                        passwordMatchErrorState.value = (password.value!=confirmPassword.value)||password.value.isEmpty()
                        linkingCodeErrorState.value = !viewModel.linkingCodes.contains(linkingCode.value)
                        emailErrorState.value = !email.value.isValidEmail()
                        nameErrorState.value = firstName.value.isEmpty()||lastName.value.isEmpty()
                        phoneErrorState.value = try{
                            phoneNumber.value.toLong()<10000000
                        } catch (e:Exception){
                            true
                        }
                        if(!registerErrorStates.containsValue(true)){
                            registerErrorMessageState.value = false
                            viewModel.register(
                                email.value,
                                password.value,
                                firstName.value,
                                lastName.value,
                                phoneNumber.value,
                                linkingCode.value,
                                processingState,
                                executeErrorState,
                                errorMessage
                            )
                        }
                        else registerErrorMessageState.value = true

                    },
                    enabled = !processingState.value,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px,0.px)


                ) {
                    if(!processingState.value)Text("Sign up")
                    else Text("Signing up...")
                }
            }
            Button(
                onClick = {
                    signUpState=!signUpState
                    registerErrorStates.forEach {
                        it.value = false
                    }
                },
                enabled = !processingState.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .margin(6.px,0.px)



            ) {
                if(signUpState) Text("Got an account? Sign In!")
                else Text("New User? Sign Up!")
            }
            Button(
                onClick = { },//viewModel.recover(email.value) },
                enabled = !processingState.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .margin(6.px,0.px)


            ) {
                Text("Reset password")
            }
        }

    }
}




fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return matches(emailRegex.toRegex())
}

fun String.isValidPassword(): Boolean{
    if (length<8) return false
    var capital = false
    var lower = false
    var num = false
    var whiteSpace = true
    //var symbol = false
    val required = listOf(capital,lower,num,whiteSpace)
    forEach {
        if(it.isUpperCase()) capital = true
        if(it.isLowerCase()) lower = true
        if(it.isDigit()) num = true
        if(it.isWhitespace()) whiteSpace = false
    }
    return true//!required.contains(false)
}

fun List<MutableState<Boolean>>.containsValue(value:Boolean):Boolean{
    forEach {
        if(it.value==value) return true
    }
    return false
}