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

interface Conductor : Entity<Conductor>, Person {
    companion object : Entity.Factory<Conductor>()

    val conductorUsername: String
    var password: String
    override var fullname: String
    override var joined: LocalDate
    var birth: LocalDate
    var age: Int
    var phones: String
}

object Conductors : Table<Conductor>("Conductors") {
    val conductorUsername: Column<String> =
        varchar("conductor_username").primaryKey().bindTo { it.conductorUsername }
    val password: Column<String> = varchar("password").bindTo { it.password }
    val fullname: Column<String> = varchar("fullname").bindTo { it.fullname }
    val joined: Column<LocalDate> = date("joined").bindTo { it.joined }
    val birth: Column<LocalDate> = date("birth").bindTo { it.birth }
    val age: Column<Int> = int("age").bindTo { it.age }
    val phones: Column<String> = varchar("phones").bindTo { it.phones }
}

val Database.conductors get() = this.sequenceOf(Conductors)
