package com.example.ui.class2

import com.example.dao.Class
import com.example.dao.Course
import com.example.dao.Lecturer
import com.example.internals.EntryDialog
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import ktfx.bindings.booleanBindingOf
import ktfx.coroutines.onMouseClicked
import ktfx.layouts.datePicker
import ktfx.layouts.label
import ktfx.layouts.textField
import ktfx.runLater
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class ClassEntryDialog(prefill: Class? = null) :
    EntryDialog<Class, Int>("${if (prefill == null) "Add" else "Edit"} Class", prefill) {

    val courseField: TextField
    val lecturerField: TextField
    val dateInitialPicker: DatePicker
    val dateFinalPicker: DatePicker

    var courseProperty: Property<Course> = SimpleObjectProperty(transaction { prefill?.course })
    var lecturerProperty: Property<Lecturer> =
        SimpleObjectProperty(transaction { prefill?.lecturer })

    init {
        grid.run {
            label("Course").grid(++row, 0)
            courseField = textField(courseProperty.value?.toString().orEmpty()) {
                runLater { requestFocus() }
                isEditable = false
                onMouseClicked {
                    CoursePickerDialog().showAndWait().ifPresent {
                        courseProperty.value = it
                        text = it.toString()
                    }
                }
            }.grid(row, 1)
            label("Lecturer").grid(++row, 0)
            lecturerField = textField(lecturerProperty.value?.toString().orEmpty()) {
                isEditable = false
                onMouseClicked {
                    LecturerPickerDialog().showAndWait().ifPresent {
                        lecturerProperty.value = it
                        text = it.toString()
                    }
                }
            }.grid(row, 1)
            label("Initial date").grid(++row, 0)
            dateInitialPicker = datePicker(prefill?.dateInitial ?: LocalDate.now()).grid(row, 1)
            label("Final date").grid(++row, 0)
            dateFinalPicker = datePicker(prefill?.dateFinal ?: LocalDate.now()).grid(row, 1)
        }
        dialogPane.lookupButton(OK).disableProperty().bind(
            booleanBindingOf(
                courseProperty,
                lecturerProperty,
                dateInitialPicker.valueProperty(),
                dateFinalPicker.valueProperty()
            ) {
                courseProperty.value == null ||
                    lecturerProperty.value == null ||
                    dateInitialPicker.value == null ||
                    dateFinalPicker.value == null ||
                    dateInitialPicker.value >= dateFinalPicker.value
            }
        )
        setResultConverter {
            if (it.buttonData != OK_DONE) {
                null
            } else {
                val configure: Class.() -> Unit = {
                    course = courseProperty.value
                    lecturer = lecturerProperty.value
                    dateInitial = dateInitialPicker.value
                    dateFinal = dateFinalPicker.value
                }
                transaction {
                    when (prefill) {
                        null -> Class.new(configure)
                        else -> Class[prefill.id].apply(configure)
                    }
                }
            }
        }
    }
}
