package com.example.ui.passenger

import com.example.dao.Passenger
import com.example.dao.passengers
import com.example.dao.passes
import com.example.int
import com.example.string
import com.example.ui.pass.AddPassDialog
import javafx.scene.control.Dialog
import javafx.scene.control.TableView
import ktfx.collections.mutableObservableListOf
import ktfx.controls.columns
import ktfx.controls.constrained
import ktfx.coroutines.onAction
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.contextMenu
import ktfx.layouts.stackPane
import ktfx.layouts.tableView
import org.ktorm.database.Database
import org.ktorm.entity.toCollection
import org.ktorm.entity.toList
import java.time.LocalDate

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
                    append<String>("Pass").string {
                        when {
                            db.passes.toList().any {
                                it.passenger.passengerId == passengerId &&
                                    it.dateEnd >= LocalDate.now()
                            } -> "Yes"
                            else -> "No"
                        }
                    }
                }
                contextMenu {
                    "Add Pass" {
                        val selection = this@tableView.selectionModel
                        disableProperty().bind(selection.selectedItemProperty().isNull)
                        onAction {
                            AddPassDialog(db, selection.selectedItem).showAndWait()
                                .ifPresent { this@tableView.refresh() }
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
