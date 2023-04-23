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

interface Alert : Entity<Alert> {
    companion object : Entity.Factory<Alert>()

    val alertId: Int
    var title: String
    var message: String?
    var dateStart: LocalDate
    var dateEnd: LocalDate?
    var track: Track?
    var conductor: Conductor
}

object Alerts : Table<Alert>("Alerts") {
    val alertId: Column<Int> = int("alert_id").primaryKey().bindTo { it.alertId }
    val title: Column<String> = varchar("title").bindTo { it.title }
    val message: Column<String> = varchar("message").bindTo { it.message }
    val dateStart: Column<LocalDate> = date("date_start").bindTo { it.dateStart }
    val dateEnd: Column<LocalDate> = date("date_end").bindTo { it.dateEnd }
    val trackColor: Column<String> = varchar("track_color").references(Tracks) { it.track }
    val conductorUsername: Column<String> =
        varchar("conductor_username").references(Conductors) { it.conductor }
}

val Database.alerts get() = this.sequenceOf(Alerts)
