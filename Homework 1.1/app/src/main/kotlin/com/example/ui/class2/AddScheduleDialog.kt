package com.example.ui.class2

import com.example.dao.Class
import com.example.dao.Schedule
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Dialog
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory
import ktfx.bindings.booleanBindingOf
import ktfx.collections.toObservableList
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.choiceBox
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.spinner
import ktfx.layouts.stackPane
import ktfx.runLater
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.DayOfWeek
import java.time.LocalTime

class AddScheduleDialog(scheduleClass: Class) : Dialog<Schedule>() {
    private companion object {
        val SIZE_SPINNER = 60.0
    }

    val dayChoice: ChoiceBox<DayOfWeek>
    val timeStartHourSpinner: Spinner<Int>
    val timeStartMinuteSpinner: Spinner<Int>
    val timeEndHourSpinner: Spinner<Int>
    val timeEndMinuteSpinner: Spinner<Int>

    init {
        headerTitle = "Add Schedule"
        dialogPane.content = stackPane {
            gridPane {
                hgap = 10.0
                vgap = 10.0
                label("Day").grid(0, 0)
                dayChoice = choiceBox(DayOfWeek.values().toObservableList()) {
                    runLater { requestFocus() }
                }.grid(0, 1 to 2)
                label("Start time").grid(1, 0)
                timeStartHourSpinner = spinner<Int> {
                    prefWidth = SIZE_SPINNER
                    valueFactory = IntegerSpinnerValueFactory(0, 24)
                }.grid(1, 1)
                timeStartMinuteSpinner = spinner<Int> {
                    prefWidth = SIZE_SPINNER
                    valueFactory = IntegerSpinnerValueFactory(0, 60)
                }.grid(1, 2)
                label("End time").grid(2, 0)
                timeEndHourSpinner = spinner<Int> {
                    prefWidth = SIZE_SPINNER
                    valueFactory = IntegerSpinnerValueFactory(0, 24)
                }.grid(2, 1)
                timeEndMinuteSpinner = spinner<Int> {
                    prefWidth = SIZE_SPINNER
                    valueFactory = IntegerSpinnerValueFactory(0, 60)
                }.grid(2, 2)
            }
        }
        buttons {
            cancel()
            ok().disableProperty().bind(
                booleanBindingOf(
                    dayChoice.valueProperty(),
                    timeStartHourSpinner.valueProperty(),
                    timeStartMinuteSpinner.valueProperty(),
                    timeEndHourSpinner.valueProperty(),
                    timeEndMinuteSpinner.valueProperty()
                ) {
                    dayChoice.value == null || newTimeStart >= newTimeEnd
                }
            )
        }
        setResultConverter {
            if (it.buttonData != OK_DONE) {
                null
            } else {
                transaction {
                    Schedule.new {
                        `class` = scheduleClass
                        day = dayChoice.selectionModel.selectedItem.value
                        timeStart = newTimeStart
                        timeEnd = newTimeEnd
                    }
                }
            }
        }
    }

    private val newTimeStart
        get() = LocalTime.of(timeStartHourSpinner.value, timeStartMinuteSpinner.value)

    private val newTimeEnd
        get() = LocalTime.of(timeEndHourSpinner.value, timeEndMinuteSpinner.value)
}
