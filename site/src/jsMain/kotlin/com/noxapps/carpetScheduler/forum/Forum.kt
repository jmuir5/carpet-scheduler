package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import com.varabyte.kobweb.compose.foundation.layout.*
import com.noxapps.carpetScheduler.pages.Header
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import dev.gitlive.firebase.FirebaseApp
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun Forum(
    jobHolder:MutableState<TrueJobObject>,
    app:FirebaseApp,
    user: User,
    userOrg: Organization,
    coroutineScope:CoroutineScope,
    navController: FauxNavController
){
    val pageState = remember{ mutableIntStateOf(0) }
    if (jobHolder.value.agent == User())jobHolder.value.agent =user
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(window.innerHeight.px)
        .background(Color.argb(0.2f, 169, 169, 169))
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
            Row(
                modifier = Modifier
                    .width(100.percent)
                    .height(Height.FitContent)
                    .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.SpaceBetween)
            ) {
                com.varabyte.kobweb.silk.components.forms.Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(15.percent)
                ) {
                    Text("Back")
                }

                H2(
                    attrs = Modifier
                        .margin(0.px, 0.px, 0.px, 0.px)
                        .align(Alignment.CenterVertically)
                        .toAttrs()
                ) {
                    Text("Booking Forum")
                }
                P(attrs = Modifier
                    .width(15.percent)
                    .color(Colors.Transparent)
                    .toAttrs()
                ) {
                    Text("x")
                }
            }
            Header("Core Information")
            if (pageState.value==0){
                sheet1(pageState, jobHolder, userOrg, app).Sheet()
            }
            Header("Required Information")
            if (pageState.value==1){
                sheet2(pageState, jobHolder).Sheet()
            }
            Header("Additional information")
            if (pageState.value==2){
                sheet3(pageState, jobHolder, app).Sheet()
            }

        }
    }
    if(pageState.value>2){
        pageState.value = 0
        navController.navigateTo("Calendar")
    }
}