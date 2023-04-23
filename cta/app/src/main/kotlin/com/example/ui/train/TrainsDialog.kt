package com.example.ui.train

import javafx.scene.control.Dialog
import ktfx.dialogs.headerTitle
import ktfx.layouts.stackPane

class TrainsDialog : Dialog<Unit>() {
    init {
        headerTitle = "Passengers"
        isResizable = true
        dialogPane.setPrefSize(800.0, 600.0)
        dialogPane.content = stackPane {
        }
    }
}
