package com.example.course

import com.example.dao.Course
import com.example.internals.NamedDialog
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.TextField
import ktfx.layouts.KtfxGridPane
import ktfx.layouts.label
import ktfx.layouts.textField
import org.jetbrains.exposed.sql.transactions.transaction

class CourseDialog(prefill: Course? = null) :
    NamedDialog<Course>(prefill, "${if (prefill == null) "Add" else "Edit"} Course") {

    val idField: TextField

    init {
        (dialogPane.content as KtfxGridPane).apply {
            label("ID").grid(1, 0)
            idField = textField(prefill?.id?.value.orEmpty()) {
                isDisable = prefill != null
            }.grid(1, 1)
        }
        setResultConverter {
            val configure: Course.() -> Unit = {
                name = nameField.text
            }
            transaction {
                if (it.buttonData != OK_DONE) {
                    null
                } else {
                    when (prefill) {
                        null -> Course.new(idField.text, configure)
                        else -> Course[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
