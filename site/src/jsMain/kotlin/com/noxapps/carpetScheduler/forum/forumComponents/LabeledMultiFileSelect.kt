package com.noxapps.carpetScheduler.forum.forumComponents


import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.*
import com.varabyte.kobweb.silk.components.style.toModifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.storage.storage
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.accept
import org.jetbrains.compose.web.attributes.multiple
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.files.File
import org.w3c.files.get

@Composable
fun labeledMultiFileSelect(
    label:String,
    valueHolder: SnapshotStateList<FileRefObject>,
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

    val files:SnapshotStateList<File> = remember{ mutableStateListOf() }
    //var file: File? by remember{ mutableStateOf(null)}

    var totalFiles by remember{ mutableIntStateOf(valueHolder.size)}
    var completedFiles by remember{ mutableIntStateOf(0) }

    var fileNameText = "$totalFiles File/s"
    var sizeText by remember{ mutableStateOf("")}
    var statusText = "${completedFiles}/${totalFiles} Completed"

    var actualWidth by remember{ mutableStateOf(0) }

    Column(modifier = Modifier
        .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.justifyContent(JustifyContent.SpaceEvenly)
                .background(variableBGColor)
                .padding(10.px, sideMargin.px, 10.px, sideMargin.px),
            ref = ref {
                actualWidth = it.offsetWidth
            }
        ) {
            P(
                attrs = Modifier
                    //.width(50.percent)
                    .fontWeight(FontWeight.Bold)
                    //.padding(6.px)
                    .margin(4.px, 0.px)
                    //.align(Alignment.CenterVertically)
                    .background(variableBGColor)
                    .toAttrs()
            ) {
                Text(label)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.px, 0.px)
                    .background(Colors.White)
                    .border(1.px, LineStyle.Solid, Colors.Black)
                    .justifyContent(JustifyContent.Center)
            ) {
                //picture
                FaFilePdf(
                    modifier = Modifier
                        .height(80.px)
                        .width(80.px),
                    size = IconSize.X5
                )
                Column(
                    modifier = Modifier
                        .width(50.percent)
                        .padding(0.px, 15.px)
                ) {
                    P(
                        attrs = Modifier
                            //.width(50.percent)
                            //.padding(6.px)
                            .margin(2.px, 0.px)
                            //.align(Alignment.CenterVertically)
                            //.background(variableBGColor)
                            .toAttrs()
                    ) {
                        Text(fileNameText)
                    }
                    P(
                        attrs = Modifier
                            //.width(50.percent)
                            //.padding(6.px)
                            .margin(2.px, 0.px)
                            //.align(Alignment.CenterVertically)
                            //.background(variableBGColor)
                            .toAttrs()
                    ) {
                        Text("size")

                    }
                    P(
                        attrs = Modifier
                            //.width(50.percent)
                            //.padding(6.px)
                            .margin(2.px, 0.px)
                            //.align(Alignment.CenterVertically)
                            //.background(variableBGColor)
                            .toAttrs()
                    ) {
                        Text("Status: $statusText")

                    }
                }
                Box(
                    modifier = Modifier
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
                                multiple()
                                accept("image/*")
                                onChange {
                                    val initialIndex = valueHolder.size
                                    val instant = Clock.System.now().epochSeconds
                                    totalFiles += it.target.files?.length!!

                                    (0..<it.target.files?.length!!).map { valueHolder.add(FileRefObject()) }

                                    (0..<it.target.files?.length!!).forEach { index ->
                                        val file = it.target.files?.get(index)
                                        valueHolder[initialIndex + index].status = "Processing"
                                        valueHolder[initialIndex + index].type = targetDir
                                        valueHolder[initialIndex + index].uploadInstant = "$instant$index"
                                        valueHolder[initialIndex + index].name = file?.name.toString()
                                        valueHolder[initialIndex + index].size = file?.size.toString()

                                        val fileRef =
                                            storageReference.child("${targetDir}/${instant}$index/${file?.name}")
                                        coroutineScope.launch {
                                            file?.let { fileRef.putFile(it) }
                                            MainScope().launch {
                                                completedFiles += 1
                                                valueHolder[initialIndex + index].status = "Done"
                                            }
                                        }
                                    }
                                }
                            }
                    )
                }
            }
        }
        Row (modifier = Modifier
            .width(actualWidth.px)
            .overflow(Overflow.Auto, Overflow.Auto)
        ) {
            valueHolder.forEach {
                Row(modifier = Modifier
                    .margin(2.px)
                    .padding(4.px)
                    .background(Color.argb(0.2f, 169, 169, 169))
                    .borderRadius(4.px)
                ) {
                    FaImage(modifier = Modifier
                        .align(Alignment.CenterVertically)
                    )
                    Text(it.name)
                    FaXmark(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .onClick { _-> valueHolder.remove(it) }
                    )
                }
            }
        }
    }
}