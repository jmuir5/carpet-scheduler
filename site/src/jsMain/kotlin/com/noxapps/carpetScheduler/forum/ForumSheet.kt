package Forum

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


open class ForumSheet(val pageState: MutableIntState) {

    open val enablePreviousButton = true
    open val showPreviousButton = true
    open val enableNextButton = true
    open val showNextButton = true

    open fun previousButtonOnClick(
    ){
        pageState.value--
    }
    open fun nextButtonOnClick(
    ){
        pageState.value++
    }

    @Composable
    open fun content(){}

    @Composable
    fun Sheet() {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                content()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (showPreviousButton) {
                    Button(
                        enabled = enablePreviousButton,
                        onClick = { previousButtonOnClick() },
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
                        Text("Previous")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if(showNextButton) {
                    Button(
                        enabled = enableNextButton,
                        onClick = { nextButtonOnClick() },
                        modifier = Modifier
                            .padding(24.dp)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}