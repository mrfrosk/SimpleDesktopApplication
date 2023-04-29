
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

fun main() {
    application {
        Window(onCloseRequest = ::exitApplication) {
        initDb()
            App()
        }
    }
//    getService().forEach {
//        println(it.joinToString())
//    }
}




