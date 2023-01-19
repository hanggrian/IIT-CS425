package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.javatime.date

object Students : PersonSchema("Students") {
    val dateGraduate = date("date_graduate").nullable()
}

class Student(id: EntityID<Int>) : IntEntity(id), PersonDocument {
    companion object : IntEntityClass<Student>(Students)

    override var name by Students.name
    override var dateJoin by Students.dateJoin
    var dateGraduate by Students.dateGraduate

    val classStudents by Registration referrersOn Registrations.studentId

    override fun toString(): String = name
}
