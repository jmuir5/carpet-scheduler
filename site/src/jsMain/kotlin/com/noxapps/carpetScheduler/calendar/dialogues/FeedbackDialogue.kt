package com.noxapps.carpetScheduler.calendar.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.generics.alertDialogue
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.document
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun FeedbackDialogue(
    jobHolder:MutableState<TrueJobObject>,
    navController: FauxNavController
){
    alertDialogue {
        H2(
            attrs = Modifier
                .margin(0.px, 0.px, 0.px, 0.px)
                .align(Alignment.CenterHorizontally)
                .toAttrs()
        ) {
            Text("Your job has been saved:")
        }

        Row(
            modifier = Modifier
                .padding(10.px)
                .justifyContent(JustifyContent.SpaceBetween)
                .fillMaxWidth()
        ) {
            com.varabyte.kobweb.silk.components.forms.Button(
                onClick = {
                    jobHolder.value = TrueJobObject()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(25.percent)
            ) {
                Text("Make another booking")
            }
            com.varabyte.kobweb.silk.components.forms.Button(
                onClick = {
                    jobHolder.value = TrueJobObject()

                    navController.popAndNavigateTo("Preview")
                },
                modifier = Modifier
                    .width(25.percent)
            ) {
                Text("ViewCalendar")
            }
        }

    }
}