package com.noxapps.carpetScheduler.adminPanel.manageOrgsPage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.generics.alertDialogue
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Composable
fun orgFeedbackDialogue(
    currentOrganisation: MutableState<Organization>,
    pageState:MutableState<Boolean>,
    navController:FauxNavController

){
    alertDialogue {
        H2(attrs = Modifier
            .margin(0.px,0.px,0.px,0.px)
            .align(Alignment.CenterHorizontally)
            .toAttrs()
        ){
            Text("the organisation \"${currentOrganisation.value.name}\" has been saved")
        }

        Row(modifier = Modifier
            .padding(10.px)
            .justifyContent(JustifyContent.SpaceBetween)
            .fillMaxWidth()){
            Button(
                onClick = {
                    pageState.value = !pageState.value
                },
                modifier = Modifier
                    .width(25.percent)
            ){
                Text("Edit another Org")
            }
            Button(
                onClick ={
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(25.percent)
            ){
                Text("Return Home")
            }
        }
    }
}