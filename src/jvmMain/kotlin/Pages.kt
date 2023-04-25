
import Service.login
import Service.registrationUser
import androidx.compose.foundation.layout.*
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

    Column {
        tableView(headers, tableData, weights, 0.9f)
        Button({ screenState.value = PagesList.Login }) {
            Text("Выйти")
        }
    }

}


