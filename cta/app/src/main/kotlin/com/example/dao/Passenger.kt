package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDate

interface Passenger : Entity<Passenger>, Person {
    companion object : Entity.Factory<Passenger>()

    val passengerId: Int
    override var fullname: String
    override var joined: LocalDate
}

object Passengers : Table<Passenger>("Passengers") {
    val passengerId: Column<Int> = int("passenger_id").primaryKey().bindTo { it.passengerId }
    val fullname: Column<String> = varchar("fullname").bindTo { it.fullname }
    val joined: Column<LocalDate> = date("joined").bindTo { it.joined }
}

val Database.passengers get() = this.sequenceOf(Passengers)
