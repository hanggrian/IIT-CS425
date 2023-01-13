package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ClassStudents : IntIdTable("ClassStudents") {
    val student = reference("student", Students)
    val `class` = reference("class", Classes)
    val grade = char("grade", 1).nullable()
}

class ClassStudent(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClassStudent>(ClassStudents)

    var student by Student referencedOn ClassStudents.student
    var `class` by Class referencedOn ClassStudents.`class`
    var grade by ClassStudents.grade
}
