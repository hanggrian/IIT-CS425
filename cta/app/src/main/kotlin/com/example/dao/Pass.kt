package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import java.time.LocalDate

interface Pass : Entity<Pass> {
    companion object : Entity.Factory<Pass>()

    val passId: Int
    var dateStart: LocalDate
    var dateEnd: LocalDate
    var passenger: Passenger
}

object Passes : Table<Pass>("Passes") {
    val passId: Column<Int> = int("pass_id").primaryKey().bindTo { it.passId }
    val dateStart: Column<LocalDate> = date("date_start").bindTo { it.dateStart }
    val dateEnd: Column<LocalDate> = date("date_end").bindTo { it.dateEnd }
    val passengerId: Column<Int> = int("passenger_id").references(Passengers) { it.passenger }
}

val Database.passes get() = this.sequenceOf(Passes)
