package com.example.ui.student

import com.example.dao.Student
import com.example.internals.PersonDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.DatePicker
import ktfx.layouts.datePicker
import ktfx.layouts.label
import org.jetbrains.exposed.sql.transactions.transaction

class StudentEntryDialog(prefill: Student? = null) :
    PersonDialog<Student>("${if (prefill == null) "Add" else "Edit"} Student", prefill) {

    val dateGraduatePicker: DatePicker

    init {
        grid.run {
            label("Graduate date").grid(++row, 0)
            dateGraduatePicker = datePicker(prefill?.dateGraduate).grid(row, 1)
        }
        dialogPane.lookupButton(OK).disableProperty().bind(nameField.textProperty().isEmpty())
        setResultConverter {
            if (it.buttonData != OK_DONE) {
                null
            } else {
                val configure: Student.() -> Unit = {
                    name = nameField.text
                    dateJoin = dateJoinPicker.value
                    dateGraduate = dateGraduatePicker.value
                }
                transaction {
                    when (prefill) {
                        null -> Student.new(configure)
                        else -> Student[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
