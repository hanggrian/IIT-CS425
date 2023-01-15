package com.example.internals

import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.beans.value.ObservableValue
import javafx.scene.control.TableColumn

fun <T> TableColumn<T, String>.stringCell(target: T.() -> String?) =
    setCellValueFactory { ReadOnlyStringWrapper(it.value.target().orEmpty()) }

@Suppress("UNCHECKED_CAST")
fun <T> TableColumn<T, Int>.intCell(target: T.() -> Int?) =
    setCellValueFactory { ReadOnlyIntegerWrapper(it.value.target() ?: 0) as ObservableValue<Int> }
