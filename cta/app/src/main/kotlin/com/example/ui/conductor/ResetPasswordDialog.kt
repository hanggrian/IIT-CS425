package com.example.ui.conductor

import com.example.dao.Conductor
import com.example.shaHash
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.Dialog
import javafx.scene.control.PasswordField
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.passwordField

class ResetPasswordDialog(conductor: Conductor) : Dialog<String>() {
    val oldPasswordField: PasswordField
    val newPasswordField: PasswordField
    val newPasswordField2: PasswordField

    init {
        headerTitle = "Reset Password"
        isResizable = true
        dialogPane.content = gridPane {
            hgap = 10.0
            vgap = 10.0
            var row = 0
            label("User name").grid(row, 0)
            label(conductor.conductorUsername).grid(row++, 1)
            label("Full name").grid(row, 0)
            label(conductor.fullname).grid(row++, 1)
            label("Old password").grid(row, 0)
            oldPasswordField = passwordField { promptText = "Old password" }.grid(row++, 1)
            label("New password").grid(row, 0)
            newPasswordField = passwordField { promptText = "New password" }.grid(row++, 1)
            label("Repeat new password").grid(row, 0)
            newPasswordField2 = passwordField { promptText = "Repeat new password" }.grid(row, 1)
        }
        setResultConverter { button ->
            if (button != OK) {
                return@setResultConverter null
            }
            if (conductor.password != "" && conductor.password != oldPasswordField.text) {
                return@setResultConverter null
            }
            if (newPasswordField.text != newPasswordField2.text) {
                return@setResultConverter null
            }
            newPasswordField.text.shaHash
        }
        buttons {
            ok()
            cancel()
        }
    }
}
