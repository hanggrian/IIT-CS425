package com.example.ui.course

import com.example.dao.Course
import com.example.internals.EntryDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.TextField
import ktfx.layouts.label
import ktfx.layouts.textField
import ktfx.runLater
import org.jetbrains.exposed.sql.transactions.transaction

class CourseEntryDialog(prefill: Course? = null) :
    EntryDialog<Course, String>("${if (prefill == null) "Add" else "Edit"} Course", prefill) {

    val nameField: TextField

    init {
        if (prefill == null) {
            idField.isDisable = false
            idField.runLater { requestFocus() }
        }
        grid.run {
            label("Name").grid(++row, 0)
            nameField = textField(prefill?.name.orEmpty()) {
                if (prefill != null) {
                    runLater { requestFocus() }
                }
                promptText = "Name"
            }.grid(row, 1)
        }
        dialogPane.lookupButton(OK).disableProperty().bind(nameField.textProperty().isEmpty())
        setResultConverter {
            if (it.buttonData != OK_DONE) {
                null
            } else {
                val configure: Course.() -> Unit = {
                    name = nameField.text
                }
                transaction {
                    when (prefill) {
                        null -> Course.new(idField.text, configure)
                        else -> Course[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
