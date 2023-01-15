package com.example.internals

import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle

/** Yes or no dialog. */
class ConfirmDialog(content: String, title: String = "Confirmation") : Dialog<ButtonType>() {
    init {
        headerTitle = title
        contentText = content
        buttons {
            no()
            yes()
        }
    }
}
