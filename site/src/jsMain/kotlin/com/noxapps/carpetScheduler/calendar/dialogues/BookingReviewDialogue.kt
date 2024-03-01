package com.noxapps.carpetScheduler.calendar.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.generics.alertDialogue
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
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
import org.w3c.dom.Document

@Composable
fun BookingReviewDialogue(
    jobObject: TrueJobObject,
    org:Organization,
    navController: FauxNavController,
    dialogueState: MutableState<Boolean>,
    feedbackDialogueState: MutableState<Boolean>,
    saveFunction:()->Unit){
    alertDialogue {
        H2(
            attrs = Modifier
                .margin(0.px, 0.px, 0.px, 0.px)
                .align(Alignment.CenterHorizontally)
                .toAttrs()
        ) {
            Text("Confirm Booking")
        }
        P(
            attrs = Modifier
                //.margin(0.px,0.px,0.px,0.px)
                //.align(Alignment.CenterHorizontally)
                .toAttrs()
        ) {
            Text("Please review the details of your booking:")
        }
        Column(
            modifier = Modifier
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
            H4(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Text("Scheduled date: ${jobObject.schedule.day}/${jobObject.schedule.month}/${jobObject.schedule.year}")
            }
            H4(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Text("Shop:")
            }
            Ul(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Li { Text("Name: ${org.name}") }
                Li { Text("Phone: ${org.phone}") }
                Li { Text("Address: ${org.address}") }
                //Li { Text("Agent phone number: ${jobObject.agent.phone}") }
            }
            H4(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Text("Booking Agent:")
            }
            Ul(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Li { Text("Name: ${jobObject.agent.name} ${jobObject.agent.surname}") }
                Li { Text("Phone: ${jobObject.agent.phone}") }
                //Li { Text("Agent phone number: ${jobObject.agent.phone}") }
            }
            H4(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Text("Job site/Client:")
            }
            Ul(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Li { Text("Client name: ${jobObject.client.name}") }
                Li { Text("Client phone: ${jobObject.client.phone}") }
                Li { Text("Address: ${jobObject.client.address}") }
                //jobObject.agent.branch?.let { Li { Text("Branch: ${it}") } }
                //Li { Text("Booking Agent: ${jobObject.agent.agent}") }
                //Li { Text("Agent phone number: ${jobObject.agent.phone}") }
            }
            H4(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Text("Job details:")
            }
            Ul(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                Li { Text("Invoice number: ${jobObject.jobDetails.invoiceNumber}") }
                Li { Text("Carpet Identifier: ${jobObject.jobDetails.carpetId}") }
                Li { Text("Meterage: ${jobObject.jobDetails.meterage}") }
                Li { Text("Underlay: ${jobObject.jobDetails.underlay}") }
                Li {
                    Text(
                        "Takeup And Disposal: ${
                            if (jobObject.jobDetails.takeup) "Yes" else "No"
                        }"
                    )
                }
                Li { Text("Furniture: ${jobObject.jobDetails.furniture}") }
                if (jobObject.jobDetails.stairs.present) {
                    Li {
                        H5(
                            attrs = Modifier
                                //.margin(0.px,0.px,0.px,0.px)
                                //.align(Alignment.CenterHorizontally)
                                .toAttrs()
                        ) {
                            Text("Stairs:")
                        }
                        Ul(
                            attrs = Modifier
                                //.margin(0.px,0.px,0.px,0.px)
                                //.align(Alignment.CenterHorizontally)
                                .toAttrs()
                        ) {
                            Li { Text("Straight: ${jobObject.jobDetails.stairs.straight}") }
                            Li { Text("Winders: ${jobObject.jobDetails.stairs.winders}") }
                            Li { Text("Bullnose: ${jobObject.jobDetails.stairs.bullnose}") }
                        }
                    }
                } else
                    Li { Text("Stairs: None") }
                if (jobObject.jobDetails.trim.presence) {
                    Li { Text("Trim: Yes") }
                    Li {
                        P {
                            Text("Trim - details: ${jobObject.jobDetails.trim.detail}")
                        }
                    }
                } else
                    Li { Text("Trim: None") }
                if (jobObject.jobDetails.access.short != "Good") {
                    Li { Text("Access: Other") }
                    Li {
                        P {
                            Text("Access - details: ${jobObject.jobDetails.access.details}")
                        }
                    }
                } else
                    Li { Text("Access: Good") }

            }
        }
        Row(
            modifier = Modifier
                .padding(10.px)
                .justifyContent(JustifyContent.SpaceBetween)
                .fillMaxWidth()
        ) {
            com.varabyte.kobweb.silk.components.forms.Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .width(25.percent)
            ) {
                Text("Change details")
            }
            com.varabyte.kobweb.silk.components.forms.Button(
                onClick = { dialogueState.value = false },
                modifier = Modifier
                    .width(25.percent)
            ) {
                Text("Change date")
            }
            com.varabyte.kobweb.silk.components.forms.Button(
                onClick = {
                    saveFunction()
                    dialogueState.value = false
                    feedbackDialogueState.value = true
                },
                modifier = Modifier
                    .width(25.percent)
            ) {
                Text("Save Booking")
            }
        }

    }
}