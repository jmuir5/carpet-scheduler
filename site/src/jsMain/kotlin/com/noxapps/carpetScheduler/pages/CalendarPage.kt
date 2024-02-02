package com.noxapps.carpetScheduler.pages


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.noxapps.carpetScheduler.calendar.CalendarViewModel
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.noxapps.carpetScheduler.JobObject
import com.noxapps.carpetScheduler.calendar.Day
import com.noxapps.carpetScheduler.calendar.DayOfWeek
import com.noxapps.carpetScheduler.calendar.VoidDay
import com.varabyte.kobweb.compose.css.CSSPercentageNumericValue
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRight
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun CalendarPage(
    viewModel: CalendarViewModel = CalendarViewModel()
){
    val currentMonth = remember { mutableStateOf(viewModel.currentMonth) }
    val currentYear = remember{mutableStateOf(viewModel.currentYear)}
    val currentMonthCalendar = remember{ mutableStateOf(viewModel.currentMonthCalendar) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(Height.FitContent)
        .padding(24.px)
        .justifyContent(JustifyContent.Center)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(Height.FitContent)
                //.display(DisplayStyle.Block)
                .border(2.px, LineStyle.Solid, Color.rgb(0,0,0))
                //.background(Color.rgb(0, 0, 0))
        ) {


        Row(modifier = Modifier
            .width(((100f/7))*7.percent)
            .height(Height.FitContent)
            .background(Color.rgb(255,255,255))
            .border(1.px, LineStyle.Solid, Color.rgb(0,0,0))

        ){
            Spacer()

            Row(modifier =Modifier
                .width(45.percent)
                .justifyContent(JustifyContent.SpaceBetween)
                .align(Alignment.CenterVertically)

            ) {
                FaArrowLeft(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)

                        .attrsModifier {
                            onClick {
                                viewModel.prevMonth(currentMonth, currentYear, currentMonthCalendar)
                            }
                        },
                    size =IconSize.XXL
                )
                P(attrs = Modifier
                    .textAlign(TextAlign.Center)
                    .fontSize(24.px)
                    .align(Alignment.CenterVertically)
                    .toAttrs()) {
                    Text(
                        value = "${currentMonth.value}, ${currentYear.value}",
                    )
                }
                FaArrowRight(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)

                        .attrsModifier {
                            onClick {
                                viewModel.nextMonth(currentMonth, currentYear, currentMonthCalendar)
                            }
                        },
                    size =IconSize.XXL

                )
            }
            Spacer(
            )
        }
            Row(
                modifier = Modifier
                    //.height(height = Height.FitContent)
                    .fillMaxWidth()
            ) {
                DayOfWeek.entries.forEach {
                    P(
                        attrs = Modifier
                            .width((100f/7).percent)
                            //.fillMaxHeight()
                            .background(Color.rgb(0, 255, 255))
                            .textAlign(TextAlign.Center)
                            .border(1.px, LineStyle.Solid, Color.rgb(0,0,0))
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
                    /*Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .size(2.dp)
                    .background(Color.Black)
                )*/
                    week.forEach { day ->
                        if (day == 0) VoidDay()
                        else Day(day, viewModel.getJobsByDay(day), JobObject("newJob", 3, "a"))
                        /*Spacer(modifier = Modifier
                        .fillMaxHeight()
                        .size(2.dp)
                        .background(Color.Black)
                    )*/
                    }
                }
                /*Spacer(modifier = Modifier
                .fillMaxWidth()
                .size(2.dp)
                .background(Color.Black)
            )*/
            }
        }
    }
}