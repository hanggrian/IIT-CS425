package com.example.ui.conductor

import com.example.dao.Conductor
import com.example.dao.Conductors
import com.example.dao.conductors
import com.example.string
import javafx.scene.control.Dialog
import javafx.scene.control.TableView
import ktfx.collections.mutableObservableListOf
import ktfx.controls.columns
import ktfx.controls.constrained
import ktfx.coroutines.onAction
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.dialogs.infoAlert
import ktfx.layouts.contextMenu
import ktfx.layouts.stackPane
import ktfx.layouts.tableView
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import org.ktorm.entity.toCollection

class ConductorsDialog(db: Database) : Dialog<Unit>() {
    val conductorTable: TableView<Conductor>

    init {
        headerTitle = "Conductors"
        isResizable = true
        dialogPane.setPrefSize(600.0, 400.0)
        dialogPane.content = stackPane {
            conductorTable = tableView(db.conductors.toCollection(mutableObservableListOf())) {
                constrained()
                columns {
                    append<String>("Conductor").string { conductorUsername }
                    append<String>("Name").string { fullname }
                    append<String>("Joined").string { joined.toString() }
                }
                contextMenu {
                    "Reset Password" {
                        val selection = this@tableView.selectionModel
                        disableProperty().bind(selection.selectedItemProperty().isNull)
                        onAction {
                            ResetPasswordDialog(selection.selectedItem).showAndWait()
                                .ifPresent { password ->
                                    db.update(Conductors) {
                                        set(it.password, password)
                                        where {
                                            it.conductorUsername eq
                                                selection.selectedItem.conductorUsername
                                        }
                                    }
                                    infoAlert("Password updated.")
                                }
                        }
                    }
                }
            }
        }
        buttons {
            close()
        }
    }
}
