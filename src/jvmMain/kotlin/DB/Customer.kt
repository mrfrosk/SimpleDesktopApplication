package DB

import org.jetbrains.exposed.dao.id.IntIdTable

object Customer: IntIdTable() {
    val name = text("name")
    val address = text("address")
    val phone = text("phone")
}