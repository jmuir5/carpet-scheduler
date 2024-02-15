package com.noxapps.carpetScheduler.calendar

import com.varabyte.kobweb.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.ConciseJobObject
import com.noxapps.carpetScheduler.dataStructures.DateObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaStar
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun RowScope.Day(
    dayOfWeek:DateObject,
    today:DateObject,
    saturdayFlag:Boolean = false,
    sundayFlag:Boolean = false,
    previewFlag:Boolean = false,
    jobs:List<ConciseJobObject>,
    pendingJob: TrueJobObject,
    dialogueFlag: MutableState<Boolean>,
    saturdayDialogueFlag: MutableState<Boolean>

){
    var jobWeightTotal =0
    jobs.map { jobWeightTotal+=it.size }
    val newJobFits = when(true){
        (jobWeightTotal>=12) ->0
        (jobWeightTotal+pendingJob.size()<=12) ->1
        else-> 2
    }
    val isPast = dayOfWeek<=today
    val isToday = dayOfWeek==today
    val variableColor = if(sundayFlag)Colors.Red else Colors.Black

    var bgColor = when(newJobFits) {
        0 -> Colors.LightGray
        //(jobWeightTotal+3/*pendingJob.size*/>12)->Color.rgb(204,204,204)
        1 -> Colors.Transparent
        2 -> Colors.Azure
        else ->Colors.AntiqueWhite
    }
    if (isPast) bgColor = Colors.LightGray

    Column(modifier = Modifier
        .fillMaxHeight()
        .width((100f/7).percent)
        .background(bgColor)
        .border(1.px, LineStyle.Solid, Colors.Black)
        .onClick {
            if(newJobFits==1&&!isPast&&!previewFlag) {
                if(saturdayFlag){
                    pendingJob.schedule = dayOfWeek
                    saturdayDialogueFlag.value = true
                }
                else if(sundayFlag){
                    Unit
                }
                else {
                    pendingJob.schedule = dayOfWeek
                    dialogueFlag.value = true
                }
            }
        }


    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.px)
            .justifyContent(JustifyContent.SpaceBetween)
        ) {
            P(
                attrs = Modifier
                    .margin(2.px, 4.px, 2.px, 4.px)
                    .color(variableColor)
                    .toAttrs()
            )
            {
                Text(value = dayOfWeek.day.toString())
            }
            if(isToday) {
                FaStar(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .margin(2.px, 4.px)
                        .color(Colors.Red)
                )
            }
        }
        Spacer(
           // modifier = Modifier.size(8.dp)
        )
        Column(modifier = Modifier
            .padding(2.px)
            .fillMaxWidth()
            //.height(Height.Inherit)
        ){
            jobsBlock(jobs)
        }
    }
}