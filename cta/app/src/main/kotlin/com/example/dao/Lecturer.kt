package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object Lecturers : PersonSchema("Lecturers")

class Lecturer(id: EntityID<Int>) : IntEntity(id), PersonDocument {
    companion object : IntEntityClass<Lecturer>(Lecturers)

    override var name by Lecturers.name
    override var dateJoin by Lecturers.dateJoin

    val classes by Class referrersOn Classes.lecturerId

    override fun toString(): String = "${id.value}. $name"
}
