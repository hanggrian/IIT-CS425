package com.example.dao

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import org.ktorm.schema.year
import java.time.Year

interface Locomotive : Entity<Locomotive> {
    companion object : Entity.Factory<Locomotive>()

    val locomotiveSerial: String
    var since: Year
}

object Locomotives : Table<Locomotive>("Locomotives") {
    val locomotiveSerial: Column<String> =
        varchar("locomotive_serial").primaryKey().bindTo { it.locomotiveSerial }
    val since: Column<Year> = year("since").bindTo { it.since }
}

val Database.locomotives get() = this.sequenceOf(Locomotives)
