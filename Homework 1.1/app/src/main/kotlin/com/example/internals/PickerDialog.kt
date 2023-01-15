package com.example.internals

import javafx.collections.transformation.FilteredList
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar.ButtonData.OK_DONE
import javafx.scene.control.ButtonType.OK
import javafx.scene.control.Dialog
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import ktfx.collections.toMutableObservableList
import ktfx.controls.isSelected
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.inputs.isDoubleClick
import ktfx.layouts.listView
import ktfx.layouts.textField
import ktfx.layouts.vbox
import ktfx.listeners.onMouseClicked
import ktfx.runLater
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.transactions.transaction

/** Choose a document with filtering support. */
abstract class PickerDialog<T : Entity<*>>(title: String, entityClass: EntityClass<*, T>) :
    Dialog<T>() {
    val searchField: TextField
    val list: ListView<T>

    abstract fun onSearch(item: T, text: String): Boolean

    init {
        val filteredList = FilteredList(
            transaction { entityClass.all().toMutableObservableList() }
        ) { true }

        headerTitle = title
        dialogPane.content = vbox {
            spacing = 10.0
            searchField = textField {
                runLater { requestFocus() }
                promptText = "Search by any property"
                textProperty().addListener { _, _, _ ->
                    if (text.isNullOrEmpty()) {
                        filteredList.setPredicate { true }
                    } else {
                        filteredList.setPredicate { onSearch(it, text.lowercase()) }
                    }
                }
            }
            list = listView(filteredList) {
                onMouseClicked {
                    if (it.isDoubleClick() && selectionModel.isSelected()) {
                        (dialogPane.lookupButton(OK) as Button).fire()
                    }
                }
            }
        }
        buttons {
            cancel()
            ok().disableProperty().bind(list.selectionModel.selectedItemProperty().isNull)
        }
        setResultConverter {
            when {
                it.buttonData != OK_DONE -> null
                else -> list.selectionModel.selectedItem
            }
        }
    }
}
