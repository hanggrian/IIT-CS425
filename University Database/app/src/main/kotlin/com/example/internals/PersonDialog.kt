package com.example.internals

import com.example.dao.PersonDocument
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import ktfx.layouts.datePicker
import ktfx.layouts.label
import ktfx.layouts.textField
import ktfx.runLater
import org.jetbrains.exposed.dao.Entity
import java.time.LocalDate

/** Maybe a [com.example.dao.Student] or [com.example.dao.Lecturer]. */
abstract class PersonDialog<T>(title: String, prefill: T?) : EntryDialog<T, Int>(title, prefill)
    where T : Entity<*>, T : PersonDocument {

    val nameField: TextField
    val dateJoinPicker: DatePicker

    init {
        grid.run {
            label("Name").grid(++row, 0)
            nameField = textField(prefill?.name.orEmpty()) {
                runLater { requestFocus() }
                promptText = "Name"
            }.grid(row, 1)
            label("Join date").grid(++row, 0)
            dateJoinPicker = datePicker(prefill?.dateJoin ?: LocalDate.now()) {
                isDisable = true
                promptText = "Join date"
            }.grid(row, 1)
        }
    }
}
