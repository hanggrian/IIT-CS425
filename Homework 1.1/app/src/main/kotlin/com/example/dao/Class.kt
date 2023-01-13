package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Classes : IntIdTable("Classes") {
    val course = reference("course", Courses)
    val lecturer = reference("lecturer", Lecturers)
    val dateInitial = date("date_initial")
    val dateFinal = date("date_final")
}

class Class(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Class>(Classes)

    var course by Course referencedOn Classes.course
    var lecturer by Lecturer referencedOn Classes.lecturer
    var dateInitial by Classes.dateInitial
    var dateFinal by Classes.dateFinal

    val schedules by Schedule referrersOn Schedules.`class`
    val classStudents by ClassStudent referrersOn ClassStudents.`class`
}
