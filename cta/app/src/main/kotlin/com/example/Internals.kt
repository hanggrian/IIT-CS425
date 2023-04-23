package com.example

import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn
import java.math.BigDecimal
import java.security.MessageDigest

val String.shaHash: String
    get() {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(toByteArray())
        return String(digest.digest())
    }

infix fun String.to(other: String?): String = if (other == null) this else "$this to $other"
infix fun String.until(other: String?): String = if (other == null) this else "$this until $other"

fun <T, E> TableColumn<T, E>.any(target: T.() -> E) =
    setCellValueFactory { ReadOnlyObjectWrapper(it.value.target()) }

fun <T> TableColumn<T, String>.string(target: T.() -> String?) =
    setCellValueFactory { ReadOnlyStringWrapper(it.value.target().orEmpty()) }

fun <T> TableColumn<T, Boolean>.boolean(target: T.() -> Boolean?) =
    setCellValueFactory { ReadOnlyBooleanWrapper(it.value.target() ?: false) }

@Suppress("UNCHECKED_CAST")
fun <T> TableColumn<T, Int>.int(target: T.() -> Int?) =
    setCellValueFactory { ReadOnlyIntegerWrapper(it.value.target() ?: 0) as ObservableValue<Int> }

fun <T> TableColumn<T, BigDecimal>.bigDecimal(target: T.() -> BigDecimal?) =
    setCellValueFactory { ReadOnlyObjectWrapper(it.value.target() ?: BigDecimal.ZERO) }
