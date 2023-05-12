import UIConponents.tableView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import service2.*

val rowSpace = 5.dp
val columnSpace = 5.dp
val rowBasicModifier = Modifier.fillMaxWidth()
val rowBasicArrangement = Arrangement.SpaceAround
val height = 60.dp

enum class PagesList {
    Login, Registration, AdminPage, UserPage, AddServicePage
}


class Authentication {
    private var emailField by mutableStateOf("")
    private var usernameField by mutableStateOf("")
    private var passwordField by mutableStateOf("")

    @Composable
    fun loginPage(screenState: MutableState<PagesList>) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
                Column(
                    Modifier.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            usernameField, { usernameField = it },
                            label = { Text("username") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            },
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            passwordField,
                            { passwordField = it },
                            label = { Text("password") }, visualTransformation = PasswordVisualTransformation()
                        )
                    }
                    Row(Modifier.align(Alignment.CenterHorizontally)) {
                        Button({
                            when (login(usernameField, passwordField)) {
                                true to true -> {
                                    screenState.value = PagesList.AdminPage
                                }

                                true to false -> {
                                    screenState.value = PagesList.UserPage
                                }
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
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
                Column(
                    Modifier.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(emailField, { emailField = it },
                            label = { Text("email") },
                            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
                        )
                    }
                    Spacer(Modifier.height(columnSpace))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            usernameField,
                            { usernameField = it },
                            label = { Text("username") },
                            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) }
                        )
                    }
                    Spacer(Modifier.height(columnSpace))
                    Row(rowBasicModifier, rowBasicArrangement) {
                        OutlinedTextField(
                            passwordField,
                            { passwordField = it },
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
                                screenState.value = PagesList.UserPage
                            }
                        }) {
                            Text("Зарегистрироваться")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun adminPage(screenState: MutableState<PagesList>) {
    var date by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var notificationMessage by remember { mutableStateOf("") }
    val expandedService = remember { mutableStateOf(false) }
    val expandedCustomer = remember { mutableStateOf(false) }
    val customers = getCustomers()
    val services = getServices()
    val customersDropDownText = remember { mutableStateOf(customers.firstOrNull()?:"") }
    val serviceDropDownText = remember { mutableStateOf(services.firstOrNull()?:"") }

    Column {
        tableView(listOf("id", "цена", "заказчик", "дата", "статус"), getOrder(), maxHeight =  0.46f)
        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxHeight(0.3f)) {
            OutlinedTextField(
                number, {
                    if (it.toIntOrNull() != null || it.isEmpty()) {
                        number = it
                    }
                },
                label = { Text("Номер заказа") }, modifier = Modifier.height(70.dp).padding(8.dp)
            )
            Button({
                notificationMessage = if (!isMatchingRegExp(date)) {
                    "Введенная вами строка в поле 'дата' не соответствует виду dddd-dd-dd"
                } else {
                    addOrder(customersDropDownText.value, date, price, isChecked)
                    ""
                }
            }, modifier = Modifier.height(56.dp).padding(8.dp)) {
                Text("Сохранить заказ")
            }
        }
        Row {
            OutlinedTextField(date, {
                date = it
            }, label = { Text("Дата") })
            Checkbox(isChecked, {
                isChecked = !isChecked
            })
            Text(" Выполнен")
        }

        Row {
            Text("Клиент")
            Spacer(Modifier.width(5.dp))
            createDropDownMenu(customersDropDownText, expandedCustomer, getCustomers())
        }

        Row {
            Text("Услуга")
            Spacer(Modifier.width(5.dp))
            createDropDownMenu(serviceDropDownText, expandedService, getServices())
        }

        Row {
            OutlinedTextField(price, {
                if (it.toIntOrNull() != null || it.isEmpty()) {
                    price = it
                }
            }, label = { Text("Цена") })
            OutlinedTextField(discount, {
                if (it.toIntOrNull() != null || it.isEmpty()) {
                    discount = it
                }
            }, label = { Text("Скидка") })
        }
        Row{
            Button({ screenState.value = PagesList.AddServicePage }) {
                Text("Добавить услугу")
            }

            Button({ screenState.value = PagesList.Login }) {
                Text("Выйти")
            }
        }
        Text(notificationMessage, Modifier.align(Alignment.CenterHorizontally))
    }

}

@Composable
fun clientAdd(screenState: MutableState<PagesList>) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.align(Alignment.CenterHorizontally).fillMaxHeight()) {
            Column(Modifier.align(Alignment.CenterVertically)) {
                OutlinedTextField(name, { name = it }, label = { Text("Наименование заказчика") })
                OutlinedTextField(address, { address = it }, label = { Text("Адрес") })
                OutlinedTextField(phone, { phone = it }, label = { Text("Телефон") })

                Button({
                    addCustomer(name, address, phone)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text("сохранить")
                }
                Row {
                    Button({ screenState.value = PagesList.Login }) {
                        Text("Выйти")
                    }
                }
            }
        }


    }
}

@Composable
fun addService(screenState: MutableState<PagesList>){
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) {
        Row{
            OutlinedTextField(name, {name = it}, label = { Text("Наименование заказа") })
        }
        Row {
            OutlinedTextField(description, {description = it}, label = { Text("Описание") })
        }
        Row{
            Button({
                insertService(name, description)
            }){
                Text("Сохранить услугу")
            }
            Button({ screenState.value = PagesList.AdminPage}) {
                Text("Добавить Заказ")
            }
            Button({ screenState.value = PagesList.Login }) {
                Text("Выйти")
            }



        }
    }
}