package Forum

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class sheet1(pageState: MutableIntState) : ForumSheet(pageState) {
    override val enablePreviousButton = false
    override val showPreviousButton = false

    private var companyName = mutableStateOf("")
    private var agentName = mutableStateOf("")
    private var agentNumber = mutableStateOf("")
    private var contactName = mutableStateOf("")
    private var contactNumber = mutableStateOf("")
    private var siteAddress = mutableStateOf("")
    @Composable
    override fun content() {
        val interactionSource = remember { MutableInteractionSource() }
        Column(modifier = Modifier
            .scrollable(rememberScrollState(), Orientation.Vertical )
        ) {
            Text("Booking Agent:", style = MaterialTheme.typography.titleLarge)
            Row(){
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 6.dp, 0.dp)
                ){
                    Text(
                        "Name:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = agentName.value,
                        interactionSource = interactionSource,
                        onValueChange = {agentName.value = it},
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    
                                    .border(
                                        1.dp,
                                        Color.DarkGray,
                                        RoundedCornerShape(percent = 30)
                                    )
                                    .padding(8.dp)
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
                Column(modifier = Modifier
                    .weight(1f)){
                    Text(
                        "Phone Number:",
                        style = MaterialTheme.typography.titleMedium)
                    
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = agentNumber.value,
                        interactionSource = interactionSource,
                        onValueChange = {agentNumber.value = it},
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    
                                    .border(
                                        1.dp,
                                        Color.DarkGray,
                                        RoundedCornerShape(percent = 30)
                                    )
                                    .padding(8.dp)
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }
            Text("Company:", style = MaterialTheme.typography.titleMedium)
            var focused1 by remember{mutableStateOf(false)}
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = companyName.value,
                interactionSource = interactionSource,
                onValueChange = {companyName.value = it},
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .onFocusChanged { focused1 = !focused1 }
                            .border(
                                1.dp,
                                if(focused1)Color.DarkGray else Color.LightGray,
                                RoundedCornerShape(percent = 30)
                            )
                            .padding(8.dp)
                    ) {
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text("Job Site:", style = MaterialTheme.typography.titleLarge)
            Row(){
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 6.dp, 0.dp)){
                    Text(
                        "Contact Name:",
                        style = MaterialTheme.typography.titleMedium)
                    
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = contactName.value,
                        interactionSource = interactionSource,
                        onValueChange = {contactName.value = it},
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    
                                    .border(
                                        1.dp,
                                        Color.DarkGray,
                                        RoundedCornerShape(percent = 30)
                                    )
                                    .padding(8.dp)
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
                Column(modifier = Modifier
                    .weight(1f)){
                    Text(
                        "Contact Phone Number:",
                        style = MaterialTheme.typography.titleMedium)
                    
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = contactNumber.value,
                        interactionSource = interactionSource,
                        onValueChange = {contactNumber.value = it},
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    
                                    .border(
                                        1.dp,
                                        Color.DarkGray,
                                        RoundedCornerShape(percent = 30)
                                    )
                                    .padding(8.dp)
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }
            Text("Address:", style = MaterialTheme.typography.titleMedium)
            
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = siteAddress.value,
                interactionSource = interactionSource,
                onValueChange = {siteAddress.value = it},
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            
                            .border(
                                1.dp,
                                Color.DarkGray,
                                RoundedCornerShape(percent = 30)
                            )
                            .padding(8.dp)
                    ) {
                        innerTextField()
                    }
                }
            )
        }
    }
}