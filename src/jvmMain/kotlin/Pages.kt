import Service.login
import Service.registrationUser
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

val rowSpace = 5.dp
val columnSpace = 5.dp
val rowBasicModifier = Modifier.fillMaxWidth()
val rowBasicArrangement = Arrangement.SpaceAround

enum class PagesList {
    Login, Registration, Main
}

@Composable
fun loginPage(screenState: MutableState<PagesList>) {
    var userField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }
    val wrongInputMessage by remember { mutableStateOf("") }
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
            Column(
                Modifier.align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(rowBasicModifier, rowBasicArrangement) {
                    OutlinedTextField(userField, { input: String -> userField = input },
                        label = { Text("username") },
                        leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) }
                    )
                }
                Spacer(Modifier.height(columnSpace))
                Row(rowBasicModifier, rowBasicArrangement) {
                    OutlinedTextField(
                        passwordField,
                        { input: String -> passwordField = input },
                        label = { Text("password") }, visualTransformation = PasswordVisualTransformation()
                    )
                }
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Button({
                        if (login(userField, passwordField)) {
                            screenState.value = PagesList.Main
                        }
                    }) {
                        Text("Войти")
                    }
                    Spacer(Modifier.width(rowSpace))
                    Button({ screenState.value = PagesList.Registration }) {
                        Text("Зарегистрироваться")
                    }
                }
            }
        }
    }
}

@Composable
fun registrationPage(screenState: MutableState<PagesList>) {
    var emailField by remember { mutableStateOf("") }
    var usernameField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
            Column(
                Modifier.align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(rowBasicModifier, rowBasicArrangement) {
                    OutlinedTextField(emailField, { input: String -> emailField = input },
                        label = { Text("email") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
                    )
                }
                Spacer(Modifier.height(columnSpace))
                Row(rowBasicModifier, rowBasicArrangement) {
                    OutlinedTextField(
                        usernameField,
                        { input: String -> usernameField = input },
                        label = { Text("username") },
                        leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) }
                    )
                }
                Spacer(Modifier.height(columnSpace))
                Row(rowBasicModifier, rowBasicArrangement) {
                    OutlinedTextField(
                        passwordField,
                        { input: String -> passwordField = input },
                        label = { Text("password") }, visualTransformation = PasswordVisualTransformation()
                    )
                }
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Button({ screenState.value = PagesList.Login }) {
                        Text("Войти")
                    }
                    Spacer(Modifier.width(rowSpace))
                    Button({
                        if (registrationUser(emailField, usernameField, passwordField)) {
                            screenState.value = PagesList.Main
                        }
                    }) {
                        Text("Зарегистрироваться")
                    }
                }
            }
        }
    }
}

@Composable
fun mainPage(screenState: MutableState<PagesList>) {
    val size = 10
    val headers = List(size) { "item $it" }
    val tableData = List(50) { List(size) { "tableItem $it" } }
    val weights = List(size) { 1f }

    Column(Modifier.verticalScroll(rememberScrollState()).fillMaxHeight()) {
        table(headers, tableData, weights)
        Button({ screenState.value = PagesList.Login }) {
            Text("Выйти")
        }
    }
}


@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    Text(
        text = text,
        modifier = Modifier.border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun table(
    headers: List<String>,
    tableData: List<List<String>>,
    cellWeights: List<Float> = List(headers.size) { 0.5f }
) {
    if (headers.size != tableData[0].size || headers.size != cellWeights.size) {
        throw Exception("количество полей в header, cellWeights или в таблице")
    }
    Column {
        Row {
            for (i in headers.indices) {
                TableCell(headers[i], cellWeights[i])
            }
        }

        Column(Modifier.height(289.dp).verticalScroll(rememberScrollState())) {
            for (row in tableData) {
                Row {
                    for (i in row.indices) {
                        TableCell(row[i], cellWeights[i])
                    }
                }
            }
        }
    }
}
