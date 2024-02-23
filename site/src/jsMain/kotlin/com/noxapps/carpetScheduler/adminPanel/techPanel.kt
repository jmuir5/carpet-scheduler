package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.login.isValidEmail
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.auth.auth
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.number
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun techPanel(
    coroutineScope: CoroutineScope,
    app: FirebaseApp,
    user: User,
    navController: FauxNavController,
    viewModel: PanelViewModel = PanelViewModel(coroutineScope, app, user, navController)
){

    var loadAttempted by remember{mutableStateOf(false)}
    val jobPaneLoadedFlag = remember {mutableStateOf(false)}
    val jobsPaneContent = remember{listOf(
        mutableListOf<TrueJobObject>(),
        mutableListOf<TrueJobObject>(),
        mutableListOf<TrueJobObject>())
    }

    Box(modifier = Modifier //conatiner
    .fillMaxWidth()
    .minHeight(100.percent)
    .position(Position.Absolute)
    .background(Color.argb(0.2f, 169, 169, 169))
) {
        if(window.window.innerWidth>900){
            Row(modifier = Modifier
                .align(Alignment.Center)
                .minWidth(80.percent)
                .minHeight((window.innerHeight*0.8).px)
                .border(
                    width = 3.px,
                    color = Colors.Black,
                    style = LineStyle.Solid
                )
                .padding(10.px)
            ) {
                Column(modifier = Modifier
                    .width(50.percent)
                    .border {
                        width(1.px)
                        color(Colors.LightGray)
                        style(right = LineStyle.Solid)
                    }
                    .padding(10.px)
                ) {
                    
                    H4() {
                        Text("Go To:")
                    }

                    Button(
                        onClick = {
                            navController.navigateTo(Routes().forumPage)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .margin(6.px, 0.px)
                    ) {
                        Text("Make New Booking")
                    }
                    Button(
                        onClick = {
                            navController.navigateTo(Routes().previewCal)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .margin(6.px, 0.px)
                    ) {
                        Text("Preview Calendar")
                    }

                    H4() {
                        Text("Account:")
                    }
                    Button(
                        onClick = {
                            //navController.navigateTo(Routes().forumPage)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .margin(6.px, 0.px)
                    ) {
                        Text("Account Options")
                    }
                    Button(
                        onClick = {
                            //panelViewModel.signOut {
                            //    Firebase.auth(app).signOut()

                            navController.popBackStack()
                            //}
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .margin(6.px, 0.px)
                    ) {
                        Text("Sign Out")
                    }

                }

                Column(modifier = Modifier
                    .width(50.percent)
                    .border {
                        width(1.px)
                        color(Colors.LightGray)
                        style(left = LineStyle.Solid)
                    }
                    .padding(10.px)
                ) {
                    H4() {
                        Text("Upcoming Jobs:")
                    }
                    Column(modifier = Modifier
                        .height(500.px)
                        .border(
                            1.px,
                            LineStyle.Solid,
                            Colors.Black
                        )
                        .padding(10.px)
                        .fillMaxWidth()
                        .overflow(Overflow.Auto, Overflow.Auto)
                    ) {
                        if(!jobPaneLoadedFlag.value){
                            LoadingNotice()
                            if(!loadAttempted){
                                loadAttempted=true
                                viewModel.getJobs(
                                    jobsPaneContent,
                                    jobPaneLoadedFlag
                                )
                            }
                        }
                        else{
                            H5(attrs = Modifier
                                .position(Position.Sticky)
                                .toAttrs()
                            ){
                                Text("Today:")
                            }
                            jobsPaneContent[0].forEach {
                                jobCard(it, navController)
                            }
                            H5(attrs = Modifier
                                .position(Position.Sticky)
                                .toAttrs()
                            ){
                                Text("Next Week:")
                            }
                            jobsPaneContent[1].forEach {
                                jobCard(it, navController)
                            }
                            H5(attrs = Modifier
                                .position(Position.Sticky)
                                .toAttrs()
                            ){
                                Text("All future jobs:")

                            }
                            jobsPaneContent[2].forEach {
                                jobCard(it, navController)
                            }
                        }
                    }
                }
            }
        }
        else{
            Column(modifier = Modifier
                .align(Alignment.Center)
                .minWidth(50.percent)
                .border(
                    width = 3.px,
                    color = Colors.Black,
                    style = LineStyle.Solid
                )
                .padding(10.px)
            ) {
                H4() {
                    Text("Go To:")
                }

                Button(
                    onClick = {
                        navController.navigateTo(Routes().forumPage)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px, 0.px)
                ) {
                    Text("Make New Booking")
                }
                Button(
                    onClick = {
                        navController.navigateTo(Routes().previewCal)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px, 0.px)
                ) {
                    Text("Preview Calendar")
                }
                H4() {
                    Text("Admin Tools:")
                }
                Button(
                    onClick = {
                        //navController.navigateTo(Routes().forumPage)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px, 0.px)
                ) {
                    Text("Manage Organisations")
                }
                H4() {
                    Text("Upcoming Jobs:")
                }
                Column(modifier = Modifier
                    .height(500.px)
                    .border(
                        1.px,
                        LineStyle.Solid,
                        Colors.Black
                    )
                    .padding(10.px)
                    .fillMaxWidth()
                    .overflow(Overflow.Auto, Overflow.Auto)
                ) {
                    if(!jobPaneLoadedFlag.value){
                        LoadingNotice()
                        if(!loadAttempted){
                            loadAttempted=true
                            viewModel.getJobs(
                                jobsPaneContent,
                                jobPaneLoadedFlag
                            )
                        }
                    }
                    else{
                        H5(attrs = Modifier
                            .position(Position.Sticky)
                            .toAttrs()
                        ){
                            Text("Today:")
                        }
                        jobsPaneContent[0].forEach {
                            jobCard(it, navController)
                        }
                        H5(attrs = Modifier
                            .position(Position.Sticky)
                            .toAttrs()
                        ){
                            Text("Next Week:")
                        }
                        jobsPaneContent[1].forEach {
                            jobCard(it, navController)
                        }
                        H5(attrs = Modifier
                            .position(Position.Sticky)
                            .toAttrs()
                        ){
                            Text("All future jobs:")

                        }
                        jobsPaneContent[2].forEach {
                            jobCard(it, navController)
                        }
                    }
                }
                H4() {
                    Text("Account:")
                }
                Button(
                    onClick = {
                        //navController.navigateTo(Routes().forumPage)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px, 0.px)
                ) {
                    Text("Account Options")
                }
                Button(
                    onClick = {
                        //panelViewModel.signOut {
                        //    Firebase.auth(app).signOut()

                        navController.popBackStack()
                        //}
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px, 0.px)
                ) {
                    Text("Sign Out")
                }

            }
        }

    }

}