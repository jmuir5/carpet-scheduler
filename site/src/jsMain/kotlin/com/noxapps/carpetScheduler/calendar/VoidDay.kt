package com.noxapps.carpetScheduler.calendar

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.modifiers.*
import org.jetbrains.compose.web.css.*

@Composable
fun RowScope.VoidDay(){
    Box(modifier = Modifier
        //.height(Height.Inherit)
        .width((100f/7).percent)
        .background(Color.rgb(0,0,0))
        .height(164.px)
        .border(1.px, LineStyle.Solid, Color.rgb(0,0,0))


    )
}