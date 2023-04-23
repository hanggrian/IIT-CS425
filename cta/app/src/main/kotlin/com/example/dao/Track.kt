package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.varchar

interface Track : Entity<Track> {
    companion object : Entity.Factory<Track>()

    val trackColor: String
    var is24h: Boolean
}

object Tracks : Table<Track>("Tracks") {
    val trackColor: Column<String> = varchar("track_color").primaryKey().bindTo { it.trackColor }
    val is24h: Column<Boolean> = boolean("is_24h").bindTo { it.is24h }
}

val Database.tracks get() = this.sequenceOf(Tracks)
