package DB

import defaultLength
import org.jetbrains.exposed.dao.id.IntIdTable

object User: IntIdTable() {
    val email = varchar("email", defaultLength).uniqueIndex()
    val username = varchar("username", defaultLength).uniqueIndex()
    val password = varchar("password", defaultLength)
}