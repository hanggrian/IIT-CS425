package com.example.ui.lecturer

import com.example.dao.Lecturer
import com.example.internals.PersonDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ButtonType.OK
import org.jetbrains.exposed.sql.transactions.transaction

class LecturerEntryDialog(prefill: Lecturer? = null) :
    PersonDialog<Lecturer>("${if (prefill == null) "Add" else "Edit"} Lecturer", prefill) {

    init {
        dialogPane.lookupButton(OK).disableProperty().bind(nameField.textProperty().isEmpty())
        setResultConverter {
            if (it.buttonData != OK_DONE) {
                null
            } else {
                val configure: Lecturer.() -> Unit = {
                    name = nameField.text
                    dateJoin = dateJoinPicker.value
                }
                transaction {
                    when (prefill) {
                        null -> Lecturer.new(configure)
                        else -> Lecturer[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
