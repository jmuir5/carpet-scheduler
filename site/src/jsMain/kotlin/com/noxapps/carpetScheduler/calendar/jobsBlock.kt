package com.noxapps.carpetScheduler.calendar


import com.varabyte.kobweb.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.noxapps.carpetScheduler.dataStructures.ConciseJobObject
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.shapes.Rect
import com.varabyte.kobweb.silk.theme.shapes.clip
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Composable
fun ColumnScope.jobsBlock(jobs:List<ConciseJobObject>, user: User, navController:FauxNavController){
    var extraWeight =4f
    jobs.forEach {
        P(attrs = Modifier
            .padding(2.px)
            .margin(2.px, 4.px, 2.px, 4.px)
            .width(90.percent)
            .background(it.color())
            .height(it.height().px)
            .clip(shape = Rect(0,5.px))
            .onClick {_->
                if (it.organisation==user.organisation||user.organisation == "0"||user.organisation == "1") {
                    navController.navigateTo(Routes().jobPage + "/${it.id}")
                }
            }
            .toAttrs()) {
            if (it.organisation==user.organisation||user.organisation == "0"||user.organisation == "1") {
                Text(
                    value = it.title,
                )
            }
            else{
                Text(
                    value = "",
                )
            }
        }
        extraWeight-=it.weight()
    }
    P(attrs = Modifier
        //.padding(2.px)
        .margin(2.px)
        .fillMaxWidth()
        .height(calcHeight(extraWeight.toInt()).px)
        .toAttrs()) {
        Text(
            value = " ",
        )
    }

}



fun calcHeight(size:Int):Int {
    return ((size*25)+(4*(size)))
}

