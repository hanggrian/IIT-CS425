package com.example.dao

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable

object Courses : IdTable<String>("Courses"), NamedTable {
    override val id = varchar("id", 10).index().entityId()
    override val name = varchar("name", 50).index()
}

class Course(id: EntityID<String>) : Entity<String>(id), Named {
    companion object : EntityClass<String, Course>(Courses)

    override var name by Courses.name

    val classes by Class referrersOn Classes.course
}
