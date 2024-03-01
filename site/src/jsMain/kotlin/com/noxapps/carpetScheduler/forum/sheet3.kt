package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.noxapps.carpetScheduler.dataStructures.BookingAgent
import com.noxapps.carpetScheduler.dataStructures.ClientObject
import com.noxapps.carpetScheduler.dataStructures.SubfloorObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.forum.forumComponents.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import dev.gitlive.firebase.FirebaseApp
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


class sheet3(
    pageState: MutableIntState,
    private val jobHolder:MutableState<TrueJobObject>,
    private val app:FirebaseApp
) : ForumSheet(pageState) {
    private var subfloor = mutableStateOf(jobHolder.value.extraInformationObject.subfloor.short)
    private var subfloorDetails = mutableStateOf(jobHolder.value.extraInformationObject.subfloor.details)
    private var smoothedge = mutableStateOf(jobHolder.value.extraInformationObject.smoothedge)
    private var waterDamage = mutableStateOf(jobHolder.value.extraInformationObject.waterDamage)
    private var dramas = mutableStateOf(jobHolder.value.extraInformationObject.dramas)
    private var extraInfo = mutableStateOf(jobHolder.value.extraInformationObject.additionalInfo)
    private var images = jobHolder.value.extraInformationObject.images.toMutableStateList()



    override fun nextButtonOnClick() {
        jobHolder.value.extraInformationObject.subfloor = SubfloorObject(subfloor.value, subfloorDetails.value)
        jobHolder.value.extraInformationObject.smoothedge = smoothedge.value
        jobHolder.value.extraInformationObject.waterDamage = waterDamage.value
        jobHolder.value.extraInformationObject.dramas = dramas.value
        jobHolder.value.extraInformationObject.additionalInfo = extraInfo.value
        jobHolder.value.extraInformationObject.images = images.toList()
        super.nextButtonOnClick()
    }

    @Composable
    override fun content() {
        //val interactionSource = remember { MutableInteractionSource() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .flex(1,1)

        ) {
            labeledSelect("Subfloor:",subfloor, errorStates[0]){
                Option(""){ Text("") }
                Option("Wood"){ Text("Wood") }
                Option("Concrete") { Text("Concrete") }
                Option("Other") { Text("Other/Mixed (Please Explain)") }
            }
            if(subfloor.value == "Other")
                labeledDetailsInput("Subfloor Details:", subfloorDetails, errorStates[1])

            labeledCheckBox("Smoothedge required:", smoothedge)
            //dropdown
            labeledCheckBox("Previous water damage?", waterDamage)

            labeledDetailsInput("Previous Dramas?:", dramas, errorStates[2])

            labeledDetailsInput("Additional Information:", extraInfo, errorStates[2])
            labeledMultiFileSelect("Images:", images,"Images", app, errorStates[7])
        }
    }
}