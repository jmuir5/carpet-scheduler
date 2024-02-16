package com.noxapps.carpetScheduler.calendar


import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.calendar.dialogues.BookingReviewDialogue
import com.noxapps.carpetScheduler.calendar.dialogues.FeedbackDialogue
import com.noxapps.carpetScheduler.calendar.dialogues.LoadingDialogue
import com.noxapps.carpetScheduler.calendar.dialogues.SaturdayDialogue
import com.varabyte.kobweb.compose.foundation.layout.*
import com.noxapps.carpetScheduler.dataStructures.DateObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRight
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import dev.gitlive.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.number

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import kotlinx.datetime.*


@Composable
fun CalendarPage(
    currentJob: MutableState<TrueJobObject>,
    coroutineScope: CoroutineScope,
    app:FirebaseApp,
    navController: FauxNavController,
    previewFlag:Boolean=false,
    viewModel: CalendarViewModel = CalendarViewModel(coroutineScope, app)
){
    val currentMonth = remember { mutableStateOf(viewModel.todayMonth) }
    val currentYear = remember{mutableStateOf(viewModel.todayYear)}
    val currentMonthCalendar = remember{ mutableStateOf(viewModel.currentMonthCalendar) }

    val dialogueFlag = remember{ mutableStateOf(false) }
    val saturdayDialogueFlag = remember{ mutableStateOf(false) }
    val feedbackFlag = remember{mutableStateOf(false)}

    var loadAttempted by remember{mutableStateOf(false)}

    val activeMonthLoadedFlag = remember {mutableStateOf(false)}
    var activeMonthsJobs = remember{mutableListOf<TrueJobObject>()}


    println("page loaded")

    if(!activeMonthLoadedFlag.value) {
        println("dialogue shown,: ${activeMonthLoadedFlag.value}")
        LoadingDialogue()
        if(!loadAttempted){
            loadAttempted=true
            viewModel.getMonthsJobs(
                currentMonth.value.number,
                currentYear.value,
                activeMonthsJobs,
                activeMonthLoadedFlag
            )
        }
    }
    else{
        println("calendar shown,: ${activeMonthLoadedFlag.value}")

        loadAttempted=false
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Height.FitContent)
                .padding(24.px)
                .justifyContent(JustifyContent.Center)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Height.FitContent)
                    .border(2.px, LineStyle.Solid, Color.rgb(0, 0, 0))
            ) {
                Row(
                    modifier = Modifier
                        .width(((100f / 7)) * 7.percent)
                        .height(Height.FitContent)
                        .background(Color.rgb(255, 255, 255))
                        .border(1.px, LineStyle.Solid, Color.rgb(0, 0, 0)
                        )

                ) {
                    com.varabyte.kobweb.silk.components.forms.Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Text("Back")
                    }

                    Spacer()

                    Row(
                        modifier = Modifier
                            .width(45.percent)
                            .justifyContent(JustifyContent.SpaceBetween)
                            .align(Alignment.CenterVertically)

                    ) {
                        FaArrowLeft(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)

                                .attrsModifier {
                                    onClick {
                                        activeMonthLoadedFlag.value = false
                                        activeMonthsJobs.clear()
                                        viewModel.prevMonth(currentMonth, currentYear, currentMonthCalendar)
                                    }
                                },
                            size = IconSize.XXL
                        )
                        P(
                            attrs = Modifier
                                .textAlign(TextAlign.Center)
                                .fontSize(24.px)
                                .align(Alignment.CenterVertically)
                                .toAttrs()
                        ) {
                            Text(
                                value = "${currentMonth.value}, ${currentYear.value}",
                            )
                        }
                        FaArrowRight(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)

                                .attrsModifier {
                                    onClick {
                                        activeMonthLoadedFlag.value = false
                                        activeMonthsJobs.clear()
                                        viewModel.nextMonth(currentMonth, currentYear, currentMonthCalendar)
                                    }
                                },
                            size = IconSize.XXL

                        )
                    }
                    Spacer()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    DayOfWeek.entries.forEach {
                        P(
                            attrs = Modifier
                                .width((100f / 7).percent)
                                //.fillMaxHeight()
                                .background(Color.rgb(0, 255, 255))
                                .textAlign(TextAlign.Center)
                                .border(1.px, LineStyle.Solid, Color.rgb(0, 0, 0))
                                .margin(0.px)
                                .toAttrs()
                        ) {
                            Text(
                                value = it.name
                            )
                        }
                    }
                }
                currentMonthCalendar.value.forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        week.forEachIndexed() { index, day ->
                            if (day == 0) VoidDay()
                            else Day(
                                DateObject(day, currentMonth.value.number, currentYear.value),
                                DateObject(viewModel.todayDay, viewModel.todayMonth.number, viewModel.todayYear),
                                index==6,
                                index==0,
                                previewFlag,
                                viewModel.getJobsByDay(day, activeMonthsJobs),
                                currentJob.value,
                                dialogueFlag,
                                saturdayDialogueFlag
                            )
                        }
                    }
                }
            }
        }
    }
    if(dialogueFlag.value){
        BookingReviewDialogue(
            jobObject = currentJob.value,
            dialogueState = dialogueFlag,
            feedbackDialogueState = feedbackFlag,
            navController = navController){
            val uniqueID = currentJob.value.jobDetails.invoiceNumber + Clock.System.now().epochSeconds.toString()
            currentJob.value.id = uniqueID
            coroutineScope.launch {
                viewModel.database.child("Jobs").child(uniqueID).setValue(currentJob.value)
            }
        }
    }
    if(saturdayDialogueFlag.value){
        SaturdayDialogue(
            nextDialogueState = dialogueFlag,
            thisDialogueState = saturdayDialogueFlag
        )
    }
    if(feedbackFlag.value){
        FeedbackDialogue(
            jobHolder = currentJob,
            navController= navController
        )
    }

}