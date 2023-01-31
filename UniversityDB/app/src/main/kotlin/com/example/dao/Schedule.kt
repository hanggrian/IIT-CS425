package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.time
import java.time.DayOfWeek

object Schedules : IntIdTable("Schedules") {
    val classId = reference("class_id", Classes)
    val day = integer("day")
    val timeStart = time("time_start")
    val timeEnd = time("time_end")
}

class Schedule(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Schedule>(Schedules)

    var `class` by Class referencedOn Schedules.classId
    var day by Schedules.day
    var timeStart by Schedules.timeStart
    var timeEnd by Schedules.timeEnd

    override fun toString(): String = "${DayOfWeek.of(day)} ($timeStart - $timeEnd)"
}
