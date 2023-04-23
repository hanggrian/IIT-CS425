package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface Wagon : Entity<Wagon> {
    companion object : Entity.Factory<Wagon>()

    val wagonId: String
    var seats: Int
}

object Wagons : Table<Wagon>("Wagons") {
    val wagonId: Column<String> = varchar("wagon_id").primaryKey().bindTo { it.wagonId }
    val seats: Column<Int> = int("seats").bindTo { it.seats }
}

val Database.wagons get() = this.sequenceOf(Wagons)
