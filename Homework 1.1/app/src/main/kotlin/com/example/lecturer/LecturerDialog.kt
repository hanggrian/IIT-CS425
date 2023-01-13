package com.example.lecturer

import com.example.dao.Lecturer
import com.example.internals.PersonDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import org.jetbrains.exposed.sql.transactions.transaction

class LecturerDialog(prefill: Lecturer? = null) :
    PersonDialog<Lecturer>(prefill, "${if (prefill == null) "Add" else "Edit"} Lecturer") {

    init {
        setResultConverter {
            val configure: Lecturer.() -> Unit = {
                name = nameField.text
                dateJoin = dateJoinPicker.value
            }
            transaction {
                if (it.buttonData != OK_DONE) {
                    null
                } else {
                    when (prefill) {
                        null -> Lecturer.new(configure)
                        else -> Lecturer[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
