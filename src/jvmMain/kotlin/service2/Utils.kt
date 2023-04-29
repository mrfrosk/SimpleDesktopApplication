package service2

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun isMatchingRegExp(string: String): Boolean {
    return Regex("\\d{2}-\\d{2}-\\d{4}").matches(string)
}

@Composable
fun createDropDownMenu(
    text: MutableState<String>, expanded: MutableState<Boolean> = mutableStateOf(false),
    data: List<String>
) {
    Box {
        Text(
            text.value, modifier = Modifier.fillMaxWidth(0.2f).clickable(onClick = { expanded.value = true })
                .border(1.dp, Color.Black)
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            data.forEach {
                DropdownMenuItem({
                    text.value = it
                    expanded.value = false
                }) {
                    Text(it)
                }
            }

        }
    }
}