package com.example.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.time

object Schedules : IntIdTable("Schedules") {
    val `class` = reference("class", Classes)
    val day = integer("day")
    val timeStart = time("time_start")
    val timeEnd = time("time_end")
}

class Schedule(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Schedule>(Schedules)

    var `class` by Class referencedOn Schedules.`class`
    var day by Schedules.day
    var timeStart by Schedules.timeStart
    var timeEnd by Schedules.timeEnd
}
