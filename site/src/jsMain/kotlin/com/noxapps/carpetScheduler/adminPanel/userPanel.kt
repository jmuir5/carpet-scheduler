package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import dev.gitlive.firebase.FirebaseApp
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Composable
fun userPanel(
    coroutineScope: CoroutineScope,
    app: FirebaseApp,
    user: MutableState<User>,
    userOrg: MutableState<Organization>,
    navController: FauxNavController,
    viewModel: PanelViewModel = PanelViewModel(coroutineScope, app, user,userOrg, navController)
){

    Box(modifier = Modifier //conatiner
    .fillMaxWidth()
    .minHeight(100.percent)
    .position(Position.Absolute)
    .background(Color.argb(0.2f, 169, 169, 169))
) {
        if(window.window.innerWidth>900){
            Column(modifier = Modifier
                .align(Alignment.Center)
                .minWidth(80.percent)
                .minHeight((window.innerHeight*0.8).px)
                .border(
                    width = 3.px,
                    color = Colors.Black,
                    style = LineStyle.Solid
                )
                .padding(10.px)){
                H4(attrs = Modifier
                    .align(Alignment.CenterHorizontally)
                    .toAttrs()
                ){
                    Text("Welcome, ${user.value.name}")
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
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

                        navPane(navController)
                        if (viewModel.accountType=="Admin") adminPane(navController)
                        accountPane(
                            navController,
                            viewModel
                        )

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
                        upcomingJobPane(
                            navController,
                            viewModel
                        )
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
                navPane(navController)
                if (viewModel.accountType=="Admin") adminPane(navController)
                upcomingJobPane(
                    navController,
                    viewModel
                )
                accountPane(
                    navController,
                    viewModel
                )

            }
        }

    }

}