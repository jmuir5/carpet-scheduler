package com.noxapps.carpetScheduler.jobPage

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.adminPanel.loadingNotice
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.noxapps.carpetScheduler.forum.forumComponents.labeledFileSelect
import com.noxapps.carpetScheduler.generics.alertDialogue
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowLeft
import dev.gitlive.firebase.FirebaseApp
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun jobPage(
    jobId:String,
    navController: FauxNavController,
    app: FirebaseApp,
    viewModel: JobViewModel = JobViewModel(jobId,app, navController, rememberCoroutineScope())
) {
    val coroutineScope = rememberCoroutineScope()
    var actualWidth = 0


    var cutSheetRecomposeBit by remember{ mutableStateOf(false) }
    var cutSheetUploadState by remember{ mutableStateOf(false) }
    var cutSheetErrorState by remember { mutableStateOf(false) }
    val newCutSheet = remember{mutableStateOf(viewModel.thisJob.value.cutSheet)}


    var floorPlanRecomposeBit by remember{ mutableStateOf(false) }
    var floorPlanUploadState by remember{ mutableStateOf(false) }
    var floorPlanErrorState by remember { mutableStateOf(false) }
    val newFloorPlan = remember{mutableStateOf(viewModel.thisJob.value.floorPlan)}

    var imageUploadState by remember{ mutableStateOf(false) }
    var imageErrorState by remember { mutableStateOf(false) }



    if(!viewModel.recomposeBit.value){
        loadingNotice("job")
    }
    else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .justifyContent(JustifyContent.Center)
                .background(Color.argb(0.2f, 169, 169, 169))
        ) {
            val mod1 = Modifier
                .width(900.px)
                .border(
                    2.px,
                    LineStyle.Solid,
                    Colors.Black
                )
                .background(Colors.White)
                .padding(10.px)
            val mod2 = Modifier
                .width(100.percent)
                .border(
                    2.px,
                    LineStyle.Solid,
                    Colors.Black
                )
                .background(Colors.White)
                .padding(10.px)
            println(window.window.innerWidth)
            Column(
                modifier = if (window.window.innerWidth > 900) mod1 else mod2,

                ref = ref {

                    actualWidth = it.offsetWidth
                }

            ) {
                Row(
                    modifier = Modifier
                        .width(100.percent)
                        .height(Height.FitContent)
                        .justifyContent(JustifyContent.SpaceBetween)
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
                        Text("Job Information")
                    }
                    P(
                        attrs = Modifier
                            .width(15.percent)
                            .color(Colors.Transparent)
                            .toAttrs()
                    ) {
                        Text("x")
                    }
                }
                P(
                    attrs = Modifier
                        //.margin(0.px,0.px,0.px,0.px)
                        //.align(Alignment.CenterHorizontally)
                        .toAttrs()
                ) {
                    Text("Details for job #$jobId")
                }

                H4(
                    attrs = Modifier
                        //.margin(0.px,0.px,0.px,0.px)
                        //.align(Alignment.CenterHorizontally)
                        .toAttrs()
                ) {
                    Text("Scheduled date: ${viewModel.thisJob.value.schedule.day}/${viewModel.thisJob.value.schedule.month}/${viewModel.thisJob.value.schedule.year}")
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
                    Li { Text("Name: ${viewModel.jobOrg.value.name}") }
                    Li { Text("Phone: ${viewModel.jobOrg.value.phone}") }
                    Li { Text("Address: ${viewModel.jobOrg.value.address}") }
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
                    Li { Text("Name: ${viewModel.thisJob.value.agent.name} ${viewModel.thisJob.value.agent.surname}") }
                    Li { Text("Phone: ${viewModel.thisJob.value.agent.phone}") }
                    //Li { Text("Agent phone number: ${jobObject.agent.phone}") }
                }
                H4(
                    attrs = Modifier
                        //.margin(0.px,0.px,0.px,0.px)
                        //.align(Alignment.CenterHorizontally)
                        .toAttrs()
                ) {
                    Text("Floor Plan")
                }
                if(!floorPlanRecomposeBit) {
                    if (viewModel.thisJob.value.floorPlan != FileRefObject()) {
                        com.varabyte.kobweb.silk.components.forms.Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                val attachment = viewModel.thisJob.value.floorPlan
                                coroutineScope.launch {
                                    viewModel.downloadFile(attachment)
                                }
                            }
                        ) {
                            Text("Download Floor plan")
                        }
                    } else {
                        if (!floorPlanUploadState) {
                            com.varabyte.kobweb.silk.components.forms.Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                onClick = {
                                    val attachment = viewModel.thisJob.value.floorPlan
                                    coroutineScope.launch {
                                        floorPlanUploadState = !floorPlanUploadState
                                    }
                                }
                            ) {
                                Text("Upload Floor plan")
                            }
                        } else {
                            labeledFileSelect("Floor Plan", newFloorPlan, "FloorPlans", app, floorPlanErrorState)
                            com.varabyte.kobweb.silk.components.forms.Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                onClick = {
                                    floorPlanErrorState = (newFloorPlan.value.status == "Processing")
                                    if(!floorPlanErrorState) {
                                        val newJobObject = viewModel.thisJob.value
                                        newJobObject.floorPlan = newFloorPlan.value
                                        coroutineScope.launch {
                                            viewModel.saveChanges(newJobObject)
                                        }
                                        floorPlanRecomposeBit = true
                                        floorPlanUploadState = true
                                    }
                                }
                            ) {
                                Text("Save Changes")
                            }
                        }
                    }
                }
                else{
                    floorPlanRecomposeBit = false
                }
                H4(
                    attrs = Modifier
                        //.margin(0.px,0.px,0.px,0.px)
                        //.align(Alignment.CenterHorizontally)
                        .toAttrs()

                ) {
                    Text("Cut Sheet")
                }
                if(!cutSheetRecomposeBit) {
                    if (viewModel.thisJob.value.cutSheet != FileRefObject()) {
                        com.varabyte.kobweb.silk.components.forms.Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                val attachment = viewModel.thisJob.value.cutSheet
                                coroutineScope.launch {
                                    viewModel.downloadFile(attachment)
                                }
                            }
                        ) {
                            Text("Download Cut Sheet")
                        }
                    } else {
                        if (!cutSheetUploadState) {
                            com.varabyte.kobweb.silk.components.forms.Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                onClick = {
                                    cutSheetUploadState = !cutSheetUploadState
                                }
                            ) {
                                Text("Upload Cut Sheet")
                            }
                        } else {
                            labeledFileSelect("Cut Sheet", newCutSheet, "CutSheets", app, cutSheetErrorState)
                            com.varabyte.kobweb.silk.components.forms.Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                onClick = {
                                    cutSheetErrorState = (newCutSheet.value.status == "Processing")
                                    if(!cutSheetErrorState) {
                                        val newJobObject = viewModel.thisJob.value
                                        newJobObject.cutSheet = newCutSheet.value
                                        coroutineScope.launch {
                                            viewModel.saveChanges(newJobObject)
                                        }
                                        cutSheetRecomposeBit = true
                                        cutSheetUploadState = true
                                    }
                                }
                            ) {
                                Text("Save Changes")
                            }

                        }
                    }
                }
                else{
                    cutSheetRecomposeBit = false
                }

                H4(
                    attrs = Modifier
                        //.margin(0.px,0.px,0.px,0.px)
                        //.align(Alignment.CenterHorizontally)
                        .toAttrs() {

                        }

                ) {
                    Text("Images")
                }
                if (viewModel.imagesLoaded.value) {
                    Row(
                        modifier = Modifier
                            .width(100.percent)
                            //.maxWidth(60.percent)
                            .overflow(Overflow.Auto, Overflow.Auto)

                    ) {
                        viewModel.jobImages.forEach {
                            Image(
                                modifier = Modifier
                                    .onClick { _ ->
                                        window.open(it)
                                    },
                                src = it,
                                height = 200,
                                width = 200,
                            )
                        }
                    }
                } else {
                    P() {
                        Text("Loading Images")
                    }
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
                    Li { Text("Client name: ${viewModel.thisJob.value.client.name}") }
                    Li { Text("Client phone: ${viewModel.thisJob.value.client.phone}") }
                    Li { Text("Address: ${viewModel.thisJob.value.client.address}") }
                    //viewModel.thisJob.value.agent.branch?.let { Li { Text("Branch: ${it}") } }
                    //Li { Text("Booking Agent: ${viewModel.thisJob.value.agent.agent}") }
                    //Li { Text("Agent phone number: ${viewModel.thisJob.value.agent.phone}") }
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
                    Li { Text("Invoice number: ${viewModel.thisJob.value.jobDetails.invoiceNumber}") }
                    Li { Text("Carpet Identifier: ${viewModel.thisJob.value.jobDetails.carpetId}") }
                    Li { Text("Meterage: ${viewModel.thisJob.value.jobDetails.meterage}") }
                    Li { Text("Underlay: ${viewModel.thisJob.value.jobDetails.underlay}") }
                    Li {
                        Text(
                            "Takeup And Disposal: ${
                                if (viewModel.thisJob.value.jobDetails.takeup) "Yes" else "No"
                            }"
                        )
                    }
                    Li { Text("Furniture: ${viewModel.thisJob.value.jobDetails.furniture}") }
                    if (viewModel.thisJob.value.jobDetails.stairs.present) {
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
                                Li { Text("Straight: ${viewModel.thisJob.value.jobDetails.stairs.straight}") }
                                Li { Text("Winders: ${viewModel.thisJob.value.jobDetails.stairs.winders}") }
                                Li { Text("Bullnose: ${viewModel.thisJob.value.jobDetails.stairs.bullnose}") }
                            }
                        }
                    } else
                        Li { Text("Stairs: None") }
                    if (viewModel.thisJob.value.jobDetails.trim.presence) {
                        Li { Text("Trim: Yes") }
                        Li {
                            P {
                                Text("Trim - details: ${viewModel.thisJob.value.jobDetails.trim.detail}")
                            }
                        }
                    } else
                        Li { Text("Trim: None") }
                    if (viewModel.thisJob.value.jobDetails.access.short != "Good") {
                        Li { Text("Access: Other") }
                        Li {
                            P {
                                Text("Access - details: ${viewModel.thisJob.value.jobDetails.access.details}")
                            }
                        }
                    } else
                        Li { Text("Access: Good") }

                }

            }


        }
    }
}