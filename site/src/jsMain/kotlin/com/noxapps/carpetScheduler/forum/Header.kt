package com.noxapps.carpetScheduler.pages


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
fun Header(text:String){
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .background(Color.rgb(176, 99, 224))
            .padding(10.px,24.px,10.px,24.px)
            .border{
                color(Color.rgb(179, 179, 179))
                style(bottom = LineStyle.Solid)
                width(2.px)
            }
            .flex(0, 0)

    ){
        H1 (attrs = Modifier
            .margin(0.px)
            .toAttrs()) {
            Text(
                value = text
            )
        }
    }

}
