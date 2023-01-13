package com.example.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

interface NamedTable {
    val name: Column<String>
}

interface Named {
    var name: String
}

abstract class PersonTable(name: String) : IntIdTable(name), NamedTable {
    override val name = varchar("name", 50).index()
    val dateJoin = date("date_join")
}

interface Person : Named {
    var dateJoin: LocalDate
}
