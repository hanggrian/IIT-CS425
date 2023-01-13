package com.example.student

import com.example.dao.Student
import com.example.internals.PersonDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.DatePicker
import ktfx.layouts.KtfxGridPane
import ktfx.layouts.datePicker
import ktfx.layouts.label
import org.jetbrains.exposed.sql.transactions.transaction

class StudentDialog(prefill: Student? = null) :
    PersonDialog<Student>(prefill, "${if (prefill == null) "Add" else "Edit"} Student") {

    val dateGraduatePicker: DatePicker

    init {
        (dialogPane.content as KtfxGridPane).apply {
            label("Graduate").grid(2, 0)
            dateGraduatePicker = datePicker(prefill?.dateGraduate).grid(2, 1)
        }
        setResultConverter {
            val configure: Student.() -> Unit = {
                name = nameField.text
                dateJoin = dateJoinPicker.value
                dateGraduate = dateGraduatePicker.value
            }
            transaction {
                if (it.buttonData != OK_DONE) {
                    null
                } else {
                    when (prefill) {
                        null -> Student.new(configure)
                        else -> Student[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
