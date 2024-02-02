package Forum

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun Forum(activeState:MutableState<Boolean>, jobHolder:MutableState<Boolean>){//<JobObject>){
    var companyName by remember{mutableStateOf("")}
    val pageState = remember{ mutableIntStateOf(0) }
    Row() {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
        ) {
            Header("Core Information")
            if (pageState.value==0){
                Box(modifier = Modifier.weight(1f)){
                    sheet1(pageState).Sheet()
                }
            }
            Header("Required Information")
            if (pageState.value==1){
                Box(modifier = Modifier.weight(1f)){
                    sheet1(pageState).Sheet()
                }
            }
            Header("Additional information")
            if (pageState.value==2){
                Box(modifier = Modifier.weight(1f)){
                    sheet1(pageState).Sheet()
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
    if(pageState.value>2){
        jobHolder.value = true
        pageState.value = 0
        activeState.value = !activeState.value
    }
}