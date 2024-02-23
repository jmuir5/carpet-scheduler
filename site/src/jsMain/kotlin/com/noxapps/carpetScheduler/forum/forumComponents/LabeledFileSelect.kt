package com.noxapps.carpetScheduler.forum.forumComponents


import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.icons.fa.FaFileImport
import com.varabyte.kobweb.silk.components.icons.fa.FaFilePdf
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.toModifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.storage.storage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.accept
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.files.File
import org.w3c.files.get

@Composable
fun labeledFileSelect(
    label:String,
    valueHolder: MutableState<FileRefObject>,
    targetDir:String,
    app: FirebaseApp,
    errorState: Boolean,
    sideMargin:Int=4
){
    val variableBGColor = if(errorState)Colors.PaleVioletRed else Colors.Transparent
    val variableBoarderColor = if(errorState)Colors.Red else Colors.Black
    val variableBoarderSize = if(errorState)3 else 1

    val storageReference = Firebase.storage(app).reference
    val coroutineScope = rememberCoroutineScope()

    var file: File? by remember{ mutableStateOf(null)}

    var fileNameText by remember{ mutableStateOf(valueHolder.value.name)}
    var sizeText by remember{ mutableStateOf(valueHolder.value.size)}
    var statusText by remember{ mutableStateOf(valueHolder.value.status)}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.justifyContent(JustifyContent.SpaceEvenly)
            .background(variableBGColor)
            .padding(10.px, sideMargin.px, 10.px, sideMargin.px)
    ) {
        P(
            attrs = Modifier
                //.width(50.percent)
                .fontWeight(FontWeight.Bold)
                //.padding(6.px)
                .margin(4.px,0.px)
                //.align(Alignment.CenterVertically)
                .background(variableBGColor)
                .toAttrs()
        ) {
            Text(label)
        }
        Row(modifier =Modifier
            .fillMaxWidth()
            .padding(4.px,0.px)
            .background(Colors.White)
            .border(1.px, LineStyle.Solid, Colors.Black)
            .justifyContent(JustifyContent.Center)
        ){
            //picture
            if (valueHolder.value.status=="N/A"){
                FaFileImport(
                    modifier = Modifier
                        .height(80.px)
                        .width(80.px),
                    size = IconSize.X5
                )
            }
            else{
                FaFilePdf(
                    modifier = Modifier
                        .height(80.px)
                        .width(80.px),
                    size = IconSize.X5
                )
            }
            Column(
                modifier =Modifier
                    .width(50.percent)
                    .padding(0.px, 15.px)
            ){
                P(
                    attrs = Modifier
                        //.width(50.percent)
                        //.padding(6.px)
                        .margin(2.px,0.px)
                        //.align(Alignment.CenterVertically)
                        //.background(variableBGColor)
                        .toAttrs()
                ) {
                    Text(fileNameText.ifEmpty { "No File Selected" })
                }
                P(
                    attrs = Modifier
                        //.width(50.percent)
                        //.padding(6.px)
                        .margin(2.px,0.px)
                        //.align(Alignment.CenterVertically)
                        //.background(variableBGColor)
                        .toAttrs()
                ) {
                    Text(statusText.ifEmpty { "0.00" }+" bytes")

                }
                P(
                    attrs = Modifier
                        //.width(50.percent)
                        //.padding(6.px)
                        .margin(2.px,0.px)
                        //.align(Alignment.CenterVertically)
                        //.background(variableBGColor)
                        .toAttrs()
                ) {
                    Text("Status: $statusText")

                }
            }
            Box(modifier =Modifier
                .align(Alignment.CenterVertically)
            ) {
                Label(
                    forId = "upload",
                    attrs = ButtonStyle.toModifier()
                        .height(80.px)
                        .width(80.px)
                        .display(DisplayStyle.Flex)
                        .alignItems(AlignItems.Center)
                        .justifyContent(JustifyContent.Center)
                        .padding(4.px)
                        .toAttrs()
                ) {
                    Text("Upload")

                }

                Input(
                    type = InputType.File,
                    attrs = InputStyle.toModifier()
                        .id("upload")
                        .height(80.px)
                        .width(80.px)
                        .opacity(0)
                        .toAttrs {
                            accept(".pdf")
                            onChange {
                                valueHolder.value.status = "Processing"
                                statusText="Processing"
                                val instant = Clock.System.now().epochSeconds
                                file = it.target.files?.get(0)

                                valueHolder.value.type = targetDir
                                valueHolder.value.uploadInstant = instant.toString()
                                valueHolder.value.name = file?.name.toString()
                                fileNameText = file?.name.toString()
                                valueHolder.value.size = file?.size.toString()
                                sizeText = file?.size.toString()


                                val fileRef = storageReference.child("${targetDir}/${instant}/${file?.name}")
                                coroutineScope.launch {
                                    file?.let{fileRef.putFile(it)}
                                    MainScope().launch {
                                        valueHolder.value.status = "Done"
                                        statusText="Done"
                                    }
                                }

                                //upload(file)
                            }
                        }
                )
            }
        }
    }
}