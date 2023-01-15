package com.example.internals

import javafx.scene.control.ButtonType.YES
import javafx.scene.control.Dialog
import javafx.scene.control.TableView
import ktfx.bindings.booleanBindingOf
import ktfx.collections.toMutableObservableList
import ktfx.controls.constrained
import ktfx.coroutines.onAction
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.layouts.contextMenu
import ktfx.layouts.separatorMenuItem
import ktfx.layouts.stackPane
import ktfx.layouts.tableView
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

/** Represents a table in database. */
abstract class SchemaDialog<T : Entity<*>>(
    title: String,
    entityClass: EntityClass<*, T>,
    private val tableObject: Table
) : Dialog<Unit>() {

    abstract fun onAdd()

    abstract fun onEdit(item: T)

    open fun onDelete(item: T) {
        transaction {
            item.delete()
            table.items -= item
        }
    }

    open fun onClear() {
        ConfirmDialog("Clear table in this dialog?").showAndWait()
            .ifPresent {
                if (it == YES) {
                    transaction { tableObject.deleteAll() }
                    table.items.clear()
                }
            }
    }

    val table: TableView<T>

    init {
        headerTitle = title
        isResizable = true
        dialogPane.setPrefSize(600.0, 400.0)
        dialogPane.content = stackPane {
            table = tableView(transaction { entityClass.all().toMutableObservableList() }) {
                constrained()
                contextMenu {
                    "Add" {
                        onAction { onAdd() }
                    }
                    "Edit" {
                        disableProperty().bind(
                            this@tableView.selectionModel.selectedItemProperty().isNull
                        )
                        onAction { onEdit(this@tableView.selectionModel.selectedItem) }
                    }
                    separatorMenuItem()
                    "Delete" {
                        disableProperty().bind(
                            this@tableView.selectionModel.selectedItemProperty().isNull
                        )
                        onAction { onDelete(this@tableView.selectionModel.selectedItem) }
                    }
                    "Clear" {
                        disableProperty().bind(
                            booleanBindingOf(this@tableView.items) {
                                this@tableView.items.isEmpty()
                            }
                        )
                        onAction { onClear() }
                    }
                }
            }
        }
        buttons {
            close()
        }
    }
}
