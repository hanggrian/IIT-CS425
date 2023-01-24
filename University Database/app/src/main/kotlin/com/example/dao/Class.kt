package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object Classes : IntIdTable("Classes") {
    val courseId = reference("course_id", Courses)
    val lecturerId = reference("lecturer_id", Lecturers)
    val dateInitial = date("date_initial")
    val dateFinal = date("date_final")
}

class Class(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Class>(Classes)

    var course by Course referencedOn Classes.courseId
    var lecturer by Lecturer referencedOn Classes.lecturerId
    var dateInitial by Classes.dateInitial
    var dateFinal by Classes.dateFinal

    val schedules by Schedule referrersOn Schedules.classId
    val classStudents by Registration referrersOn Registrations.classId

    override fun toString(): String =
        transaction { "${course.id.value}-${this@Class.id.value} ${course.name}" }
}
