package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.foundation.layout.*
import com.noxapps.carpetScheduler.pages.Header
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.gitlive.firebase.FirebaseApp
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text

@Composable
fun Forum(jobHolder:MutableState<TrueJobObject>, app:FirebaseApp, navController: FauxNavController){//<JobObject>){
    var companyName by remember{mutableStateOf("")}
    val pageState = remember{ mutableIntStateOf(0) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(window.innerHeight.px)
        .background(Colors.DarkGray)
        .justifyContent(JustifyContent.Center)
    ) {
        Column(
            modifier = Modifier
                .height(window.innerHeight.px)
                .minWidth(50.percent)
                .display(DisplayStyle.Flex)
                .flexDirection(FlexDirection.Column)
                .background(Colors.LightGrey)
        ) {
            Row (modifier = Modifier
                .fillMaxWidth()){
                com.varabyte.kobweb.silk.components.forms.Button(
                    onClick = {navController.navigateTo("Preview")},
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text("Preview Calendar")
                }
            }
            Header("Core Information")
            if (pageState.value==0){
                sheet1(pageState, jobHolder, app).Sheet()
            }
            Header("Required Information")
            if (pageState.value==1){
                sheet2(pageState, jobHolder).Sheet()
            }
            Header("Additional information")
            if (pageState.value==2){
                sheet3(pageState, jobHolder).Sheet()
            }

        }
    }
    if(pageState.value>2){
        pageState.value = 0
        navController.navigateTo("Calendar")
    }
}