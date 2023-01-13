package com.example.internals

import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.TableView
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

abstract class TableDialog<T : Entity<*>>(
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
        Dialog<ButtonType>().apply {
            headerTitle = "Are you sure?"
            contentText =
                "Confirming this action will remove all data in this table."
            buttons {
                no()
                yes()
            }
        }.showAndWait().ifPresent {
            if (it === ButtonType.YES) {
                transaction { tableObject.deleteAll() }
                table.items.clear()
            }
        }
    }

    val table: TableView<T>

    init {
        headerTitle = title
        isResizable = true
        dialogPane.content = stackPane {
            table =
                tableView(transaction { entityClass.all().toList() }.toMutableObservableList()) {
                    constrained()
                    contextMenu {
                        "Add" {
                            onAction { onAdd() }
                        }
                        "Edit" {
                            onAction { onEdit(this@tableView.selectionModel.selectedItem) }
                            disableProperty().bind(
                                this@tableView.selectionModel.selectedItemProperty().isNull
                            )
                        }
                        separatorMenuItem()
                        "Delete" {
                            onAction { onDelete(this@tableView.selectionModel.selectedItem) }
                            disableProperty().bind(
                                this@tableView.selectionModel.selectedItemProperty().isNull
                            )
                        }
                        "Clear" {
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
