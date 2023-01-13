package com.example

import javafx.scene.control.Dialog
import ktfx.dialogs.buttons

class AboutDialog : Dialog<Unit>() {

    init {
        title = "Homework 1.1"
        headerText = "by Hendra Anggrian (hwijaya@hawk.iit.edu)"
        contentText = "A JavaFX app that modify courses in university."
        buttons {
            close()
        }
    }
}
