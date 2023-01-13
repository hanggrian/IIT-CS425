package com.example.internals

import javafx.beans.property.ReadOnlyStringWrapper
import javafx.scene.control.TableColumn

fun <T> TableColumn<T, String>.stringCell(target: T.() -> String?) =
    setCellValueFactory { ReadOnlyStringWrapper(it.value.target().orEmpty()) }
