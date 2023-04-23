package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.timestamp
import org.ktorm.schema.varchar
import java.time.Instant

interface Trip : Entity<Trip> {
    companion object : Entity.Factory<Trip>()

    val tripTimestamp: Instant
    var passenger: Passenger
    var fare: Double
    var pass: Pass?
    var track: Track
    var stationNameIn: String
    var stationNameOut: String?
}

object Trips : Table<Trip>("Trips") {
    val tripTimestamp: Column<Instant> =
        timestamp("trip_timestamp").primaryKey().bindTo { it.tripTimestamp }
    val passengerId: Column<Int> = int("passenger_id").references(Passengers) { it.passenger }
    val fare: Column<Double> = double("fare").bindTo { it.fare }
    val passId: Column<Int> = int("pass_id").references(Passes) { it.pass }
    val trackColor: Column<String> = varchar("track_color").references(Tracks) { it.track }
    val stationNameIn: Column<String> = varchar("station_name_in").bindTo { it.stationNameIn }
    val stationNameOut: Column<String> = varchar("station_name_out").bindTo { it.stationNameOut }
}

val Database.trips get() = this.sequenceOf(Trips)
