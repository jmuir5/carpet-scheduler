package Forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Header(text:String){
    Column {
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = Color.Gray)
        )
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(24.dp)

        ){
            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h4,
                modifier= Modifier
            )
        }
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = Color.Gray)
        )
    }
}
