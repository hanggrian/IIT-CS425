package com.example.dao

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable

object Courses : IdTable<String>("Courses") {
    override val id = varchar("id", 10).index().entityId()
    val name = varchar("name", 50).index()
}

class Course(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Course>(Courses)

    var name by Courses.name

    val classes by Class referrersOn Classes.courseId

    override fun toString(): String = "${id.value}: $name"
}
