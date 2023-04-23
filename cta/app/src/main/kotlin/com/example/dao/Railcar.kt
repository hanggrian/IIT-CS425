package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

interface Railcar : Entity<Railcar> {
    companion object : Entity.Factory<Railcar>()

    val train: Train
    val wagon: Wagon
}

object Railcars : Table<Railcar>("Railcars") {
    val trainId: Column<String> = varchar("train_id").primaryKey().references(Trains) { it.train }
    val wagonId: Column<String> = varchar("wagon_id").references(Wagons) { it.wagon }
}

val Database.railcars get() = this.sequenceOf(Railcars)
