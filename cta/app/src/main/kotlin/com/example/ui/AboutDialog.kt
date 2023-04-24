package com.example.ui

import javafx.scene.control.Dialog
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle

class AboutDialog : Dialog<Unit>() {
    init {
        headerTitle = "CS425 - Class Project"
        contentText = "by Hendra Anggrian (hwijaya@hawk.iit.edu)"
        buttons {
            close()
        }
    }
}
