package service2

import DB.Customer
import DB.Order
import DB.Service
import DB.User
import hashAlgorithm
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


val connect = Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")

const val adminEmail = "admin@Mail.ru"
const val adminUsername = "admin"
const val adminPassword = "1"
fun initDb() = transaction {
    SchemaUtils.create(User)
    SchemaUtils.create(Customer)
    SchemaUtils.create(Service)
    SchemaUtils.create(Order)
    if (!isExistUser(adminEmail, adminUsername)) {
        User.insert {
            it[email] = adminEmail
            it[username] = adminUsername
            it[password] = Hash.encrypt(adminPassword, hashAlgorithm)
            it[isAdmin] = true
        }
    }
}

fun registrationUser(email: String, username: String, password: String, isAdmin: Boolean = false): Boolean {
    if (!isExistUser(email, username) && email != "" && username != "") {
        transaction {
            User.insert {
                it[User.email] = email
                it[User.username] = username
                it[User.password] = Hash.encrypt(password, hashAlgorithm)
                it[User.isAdmin] = isAdmin
            }
        }
        return true
    }
    return false
}

fun addCustomer(name: String, address: String, phone: String){
    transaction {
        Customer.insert {
            it[Customer.name] = name
            it[Customer.address] = address
            it[Customer.phone] = phone
        }
    }
}

fun isExistUser(email: String, username: String): Boolean {
    return transaction {
        User.select(
            (User.email eq email) and (User.username eq username)
        ).toList().isNotEmpty()
    }
}

fun login(username: String, password: String): Pair<Boolean, Boolean> {
    val isLogin = transaction {
        User.select(
            (User.password eq Hash.encrypt(password, hashAlgorithm)) and (User.username eq username)
        ).map { it[User.isAdmin] }.firstOrNull()
    }
    return if (isLogin != null) {
        Pair(true, isLogin)
    } else {
        Pair(false, false)
    }
}

