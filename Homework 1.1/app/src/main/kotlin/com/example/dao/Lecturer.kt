package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object Lecturers : PersonTable("Lecturers")

class Lecturer(id: EntityID<Int>) : IntEntity(id), Person {
    companion object : IntEntityClass<Lecturer>(Lecturers)

    override var name by Lecturers.name
    override var dateJoin by Lecturers.dateJoin

    val classes by Class referrersOn Classes.lecturer
}
