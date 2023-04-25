
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Composable
@Preview
fun App() {
    val currentScreen = remember { mutableStateOf(PagesList.Main) }

    when (currentScreen.value) {
        PagesList.Login -> loginPage(currentScreen)
        PagesList.Registration -> registrationPage(currentScreen)
        PagesList.Main -> mainPage(currentScreen)
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }

}


