package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.Text

@Composable
fun ColumnScope.upcomingJobPane(
    navController: FauxNavController,
    viewModel: PanelViewModel
){
    var loadAttempted by remember{ mutableStateOf(false) }
    val jobPaneLoadedFlag = remember { mutableStateOf(false) }
    val jobsPaneContent = remember{listOf(
        mutableListOf<TrueJobObject>(),
        mutableListOf<TrueJobObject>(),
        mutableListOf<TrueJobObject>())
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
            LoadingNotice("Jobs")
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