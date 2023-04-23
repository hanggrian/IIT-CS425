package com.example.ui.alert

import com.example.dao.Alert
import javafx.scene.control.Dialog
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle

class ViewAlertDialog(alert: Alert) : Dialog<Unit>() {
    init {
        headerTitle = "Alert #${alert.alertId}"
        isResizable = true
        dialogPane.contentText = buildString {
            appendLine("Track: ${alert.track?.trackColor.orEmpty()}")
            appendLine("Conductor: ${alert.conductor.conductorUsername}")
            appendLine("Date start: ${alert.dateStart}")
            appendLine("Date end: ${alert.dateEnd?.toString() ?: '-'}")
            alert.message?.let {
                appendLine()
                appendLine(it.trim())
            }
        }
        buttons {
            close()
        }
    }
}
