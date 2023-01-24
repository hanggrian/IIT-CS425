package com.example.internals

import javafx.scene.control.Dialog
import javafx.scene.control.TextField
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.KtfxGridPane
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.stackPane
import ktfx.layouts.textField
import org.jetbrains.exposed.dao.Entity

/** Represents an entry in table. */
abstract class EntryDialog<T : Entity<*>, ID>(title: String, prefill: T?) : Dialog<T>() {
    val grid: KtfxGridPane
    val idField: TextField

    var row: Int = 0

    init {
        headerTitle = title
        dialogPane.content = stackPane {
            grid = gridPane {
                hgap = 10.0
                vgap = 10.0
                label("ID").grid(row, 0)
                idField = textField(prefill?.id?.value?.toString().orEmpty()) {
                    isDisable = true
                    promptText = "ID"
                }.grid(row, 1)
            } as KtfxGridPane
        }
        buttons {
            cancel()
            ok()
        }
    }
}
