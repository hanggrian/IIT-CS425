package com.example.ui.alert

import com.example.dao.Alert
import com.example.dao.Conductor
import com.example.dao.conductors
import com.example.shaHash
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.Dialog
import javafx.scene.control.PasswordField
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import ktfx.collections.mutableObservableListOf
import ktfx.dialogs.buttons
import ktfx.dialogs.errorAlert
import ktfx.dialogs.headerTitle
import ktfx.layouts.choiceBox
import ktfx.layouts.datePicker
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.passwordField
import ktfx.layouts.textArea
import ktfx.layouts.textField
import ktfx.text.buildStringConverter
import org.ktorm.database.Database
import org.ktorm.entity.toCollection
import java.time.LocalDate.now

class AddAlertDialog(db: Database) : Dialog<Alert>() {
    val conductorUserChoice: ChoiceBox<Conductor>
    val conductorPassField: PasswordField
    val titleField: TextField
    val messageArea: TextArea
    val dateStartPicker: DatePicker
    val dateEndPicker: DatePicker

    init {
        headerTitle = "Add Alert"
        isResizable = true
        dialogPane.content = gridPane {
            hgap = 10.0
            vgap = 10.0
            var row = 0
            label("Conductor user").grid(row, 0)
            conductorUserChoice = choiceBox(db.conductors.toCollection(mutableObservableListOf())) {
                converter = buildStringConverter {
                    fromString { null }
                    toString { it?.conductorUsername.orEmpty() }
                }
            }.grid(row++, 1 to 2)
            label("Conductor pass").grid(row, 0)
            conductorPassField = passwordField { promptText = "Password" }.grid(row++, 1 to 2)
            label("Title").grid(row, 0)
            titleField = textField { promptText = "Title" }.grid(row++, 1 to 2)
            label("Message").grid(row, 0)
            messageArea = textArea { promptText = "Message" }.grid(row++, 1 to 2)
            label("Date").grid(row, 0)
            dateStartPicker = datePicker(now()) { promptText = "Date start" }.grid(row, 1)
            dateEndPicker = datePicker(now()) { promptText = "Date end" }.grid(row, 2)
        }
        setResultConverter { button ->
            if (button != OK) {
                return@setResultConverter null
            }
            if (conductorPassField.text.shaHash != conductorUserChoice.value.password) {
                errorAlert("Wrong password.")
                return@setResultConverter null
            }
            Alert.invoke {
                title = titleField.text
                message = messageArea.text
                dateStart = dateStartPicker.value
                dateEnd = dateEndPicker.value
                title = titleField.text
                conductor = conductorUserChoice.value
            }
        }
        buttons {
            ok()
            cancel()
        }
    }
}
