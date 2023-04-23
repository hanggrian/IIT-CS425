package com.example.ui.passenger

import com.example.dao.Passenger
import com.example.dao.passengers
import com.example.int
import com.example.string
import javafx.scene.control.Dialog
import javafx.scene.control.TableView
import ktfx.collections.mutableObservableListOf
import ktfx.controls.columns
import ktfx.controls.constrained
import ktfx.coroutines.onAction
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.contextMenu
import ktfx.layouts.separatorMenuItem
import ktfx.layouts.stackPane
import ktfx.layouts.tableView
import org.ktorm.database.Database
import org.ktorm.entity.toCollection

class PassengersDialog(db: Database) : Dialog<Unit>() {
    val passengerTable: TableView<Passenger>

    init {
        headerTitle = "Passengers"
        isResizable = true
        dialogPane.setPrefSize(600.0, 400.0)
        dialogPane.content = stackPane {
            passengerTable = tableView(db.passengers.toCollection(mutableObservableListOf())) {
                constrained()
                columns {
                    append<Int>("Passenger").int { passengerId }
                    append<String>("Name").string { fullname }
                    append<String>("Joined").string { joined.toString() }
                }
                contextMenu {
                    "Add" {
                        onAction {
                        }
                    }
                    "Edit" {
                        disableProperty().bind(
                            this@tableView.selectionModel.selectedItemProperty().isNull
                        )
                        onAction {
                        }
                    }
                    separatorMenuItem()
                    "Delete" {
                        disableProperty().bind(
                            this@tableView.selectionModel.selectedItemProperty().isNull
                        )
                        onAction {
                        }
                    }
                    "Clear" {
                        disableProperty().bind(
                            ktfx.bindings.booleanBindingOf(this@tableView.items) {
                                this@tableView.items.isEmpty()
                            }
                        )
                        onAction {
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
