package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object Registrations : IntIdTable("Registrations") {
    val classId = reference("class_id", Classes)
    val studentId = reference("student_id", Students)
    val grade = char("grade", 1).nullable()
}

class Registration(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Registration>(Registrations)

    var `class` by Class referencedOn Registrations.classId
    var student by Student referencedOn Registrations.studentId
    var grade by Registrations.grade

    override fun toString(): String = "${transaction { student }} (${grade ?: "-"})"
}
