package com.example.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

abstract class PersonSchema(name: String) : IntIdTable(name) {
    val name = varchar("name", 50).index()
    val dateJoin = date("date_join")
}

interface PersonDocument {
    var name: String
    var dateJoin: LocalDate
}
