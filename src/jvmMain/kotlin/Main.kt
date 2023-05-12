
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import service2.initDb


@Composable
@Preview
fun App() {
    val currentScreen = remember { mutableStateOf(PagesList.AdminPage) }
    val authentication = Authentication()
    when (currentScreen.value) {
        PagesList.Login -> authentication.loginPage(currentScreen)
        PagesList.Registration -> authentication.registrationPage(currentScreen)
        PagesList.UserPage -> clientAdd(currentScreen)
        PagesList.AdminPage -> adminPage(currentScreen)
        PagesList.AddServicePage -> addService(currentScreen)
    }
}
@Composable
fun testSize(){
    Row{
        Button({}, Modifier.height(50.dp)){
            Text("same_text")
        }
        Spacer(Modifier.width(10.dp))
        OutlinedTextField("value: ", {}, modifier = Modifier.height(50.dp))
    }
}
fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
            initDb()
            App()
        }
    }

}




