package com.noxapps.carpetScheduler.adminPanel.manageOrgsPage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun orgCard(
    target: Organization,
    onClick:()->Unit
    //navController: FauxNavController
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.px, 5.px)
        .onClick {
            onClick()
        }
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.px)
            .background(Colors.AliceBlue)
            .borderRadius(5.px)

        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .justifyContent(JustifyContent.SpaceBetween)

            ){
                H5(attrs = Modifier
                    .margin(0.px)
                    .toAttrs()
                ) {
                    Text(target.name)
                }
                H5(attrs = Modifier
                    .margin(0.px)
                    .toAttrs()
                ) {
                    Text(target.phone)
                }
            }
            P(attrs = Modifier
                .margin(0.px)
                .toAttrs()
            ) {
                Text(target.address)
            }
            P(attrs = Modifier
                .margin(0.px)
                .toAttrs()
            ) {
                Text("placeholder")
            }
        }
    }
}