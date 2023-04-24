package com.example.ui.pass

import com.example.dao.Pass
import com.example.dao.Passenger
import com.example.dao.passes
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.DatePicker
import javafx.scene.control.Dialog
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.datePicker
import ktfx.layouts.gridPane
import ktfx.layouts.label
import org.ktorm.database.Database
import org.ktorm.entity.add
import java.time.LocalDate.now

class AddPassDialog(db: Database, passenger: Passenger) : Dialog<Pass>() {
    val dateStartPicker: DatePicker
    val dateEndPicker: DatePicker

    init {
        headerTitle = "Add Pass"
        dialogPane.content = gridPane {
            hgap = 10.0
            vgap = 10.0
            var row = 0
            label("Date").grid(row, 0)
            dateStartPicker = datePicker(now()) { promptText = "Date start" }.grid(row, 1)
            dateEndPicker = datePicker(now()) { promptText = "Date end" }.grid(row, 2)
        }
        setResultConverter { button ->
            if (button != OK) {
                return@setResultConverter null
            }
            Pass {
                dateStart = dateStartPicker.value
                dateEnd = dateEndPicker.value
                this.passenger = passenger
            }.also { db.passes.add(it) }
        }
        buttons {
            ok()
            cancel()
        }
    }
}
