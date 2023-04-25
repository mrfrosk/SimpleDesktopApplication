import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TableViewCell(text: String, weight: Float, color: Color = Color.Black) {
    Text(
        text = text,
        modifier = Modifier.border(1.dp, color)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun tableView(
    headers: List<String>,
    data: List<List<String>>,
    weights: List<Float> = List(headers.size) { 0.5f },
    maxHeight: Float
) {
    if (headers.size != data[0].size || headers.size != weights.size) {
        throw Exception("количество полей в header, cellWeights или в таблице не совпадает")
    }
    Column(Modifier.fillMaxHeight(maxHeight)) {
        Row {
            for (i in headers.indices) {
                TableViewCell(headers[i], weights[i])
            }
        }

        Column(Modifier.verticalScroll(rememberScrollState())) {
            for (row in data) {
                Row {
                    for (i in row.indices) {
                        TableViewCell(row[i], weights[i])
                    }
                }
            }
        }
    }
}