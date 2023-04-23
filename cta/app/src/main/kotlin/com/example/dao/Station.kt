package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.double
import org.ktorm.schema.varchar

interface Station : Entity<Station> {
    companion object : Entity.Factory<Station>()

    val track: Track
    val stationName: String
    var lat: Double
    var lng: Double
    var location: String
    var zip: String
    var hasElevator: Boolean
    var hasParking: Boolean
}

object Stations : Table<Station>("Stations") {
    val trackColor: Column<String> =
        varchar("track_color").primaryKey().references(Tracks) { it.track }
    val stationName: Column<String> = varchar("station_name").primaryKey().bindTo { it.stationName }
    val lat: Column<Double> = double("lat").bindTo { it.lat }
    val lng: Column<Double> = double("lng").bindTo { it.lng }
    val location: Column<String> = varchar("location").bindTo { it.location }
    val zip: Column<String> = varchar("zip").bindTo { it.zip }
    val hasElevator: Column<Boolean> = boolean("has_elevator").bindTo { it.hasElevator }
    val hasParking: Column<Boolean> = boolean("has_parking").bindTo { it.hasParking }
}

val Database.stations get() = this.sequenceOf(Stations)
