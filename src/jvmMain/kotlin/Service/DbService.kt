package Service

import DB.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

const val hashAlgorithm = "SHA-512"
val connect = Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")

fun initDb() = transaction {
    SchemaUtils.create(User)
}

fun registrationUser(email: String, username: String, password: String): Boolean {
    if (!isExistUser(email, username) && email != "" && username != "") {
        transaction {
            User.insert {
                it[User.email] = email
                it[User.username] = username
                it[User.password] = Hash.encrypt(password, hashAlgorithm)
            }
        }
        return true
    }
    return false
}

fun isExistUser(email: String, username: String): Boolean {
    return transaction {
        User.select(
            (User.email eq email) and (User.username eq username)
        ).toList().isNotEmpty()
    }
}

fun login(username: String, password: String): Boolean {
    return transaction {
        User.select(
            (User.password eq Hash.encrypt(password, hashAlgorithm)) and (User.username eq username)
        ).toList().isNotEmpty()
    }
}

