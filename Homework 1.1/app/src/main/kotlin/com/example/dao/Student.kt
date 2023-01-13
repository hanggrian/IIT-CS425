package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.javatime.date

object Students : PersonTable("Students") {
    val dateGraduate = date("date_graduate").nullable()
}

class Student(id: EntityID<Int>) : IntEntity(id), Person {
    companion object : IntEntityClass<Student>(Students)

    override var name by Students.name
    override var dateJoin by Students.dateJoin
    var dateGraduate by Students.dateGraduate

    val classStudents by ClassStudent referrersOn ClassStudents.student
}
