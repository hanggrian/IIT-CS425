package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

interface Train : Entity<Train> {
    companion object : Entity.Factory<Train>()

    val trainId: String
    var track: Track
    var locomotive: Locomotive
    var conductor: Conductor
}

object Trains : Table<Train>("Trains") {
    val trainId: Column<String> = varchar("trainId").primaryKey().bindTo { it.trainId }
    val trackColor: Column<String> = varchar("track_color").references(Tracks) { it.track }
    val locomotiveSerial: Column<String> =
        varchar("locomotive_serial").references(Locomotives) { it.locomotive }
    val conductorUsername: Column<String> =
        varchar("conductor_username").references(Conductors) { it.conductor }
}

val Database.trains get() = this.sequenceOf(Trains)
