package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.Composable
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H5
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun jobCard(
    target: TrueJobObject,
    navController: FauxNavController
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.px, 5.px)
        .onClick {
            navController.navigateTo(Routes().jobPage+"/${target.id}")
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
                    Text(target.jobDetails.invoiceNumber)
                }
                H5(attrs = Modifier
                    .margin(0.px)
                    .toAttrs()
                ) {
                    Text(target.schedule.toString())
                }
            }
            P(attrs = Modifier
                .margin(0.px)
                .toAttrs()
            ) {
                Text(target.client.address)
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