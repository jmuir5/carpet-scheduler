package com.noxapps.carpetScheduler.calendar

import com.varabyte.kobweb.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.noxapps.carpetScheduler.JobObject
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.shapes.Rect
import com.varabyte.kobweb.silk.theme.shapes.clip
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun RowScope.Day(dayOfWeek:Int, jobs:List<JobObject>, pendingJob:JobObject){

    var jobWeightTotal =0
    jobs.map { jobWeightTotal+=it.size }

    val bgColor = when(true){
        (jobWeightTotal==12)-> Color.rgb(68,68,68)
        (jobWeightTotal+pendingJob.size>12)->Color.rgb(204,204,204)
        else ->Color.argb(0,0,0,0)
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .width((100f/7).percent)
        .background(bgColor)
        .border(1.px, LineStyle.Solid, Color.rgb(0,0,0))

    ){
        P(attrs = Modifier
            .padding(8.px)
            .margin(2.px, 4.px, 2.px, 4.px)
            .toAttrs())
        {
            Text(value = dayOfWeek.toString())
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