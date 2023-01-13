package com.example.internals

import com.example.dao.Named
import javafx.scene.control.Dialog
import javafx.scene.control.TextField
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.textField
import ktfx.runLater

abstract class NamedDialog<T : Named>(prefill: T?, title: String) : Dialog<T>() {
    val nameField: TextField

    init {
        headerTitle = title
        dialogPane.content = gridPane {
            hgap = 10.0
            vgap = 10.0
            label("Name").grid(0, 0)
            nameField = textField(prefill?.name.orEmpty()) {
                runLater { requestFocus() }
            }.grid(0, 1)
        }
        buttons {
            cancel()
            ok()
        }
    }
}
