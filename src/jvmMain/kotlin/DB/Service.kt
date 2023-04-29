package DB

import org.jetbrains.exposed.dao.id.IntIdTable

object Service: IntIdTable() {
    val name = text("name")
    val description = text("description")
}