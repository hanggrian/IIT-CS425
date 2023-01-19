package com.example.ui

import com.example.dao.Class
import com.example.dao.Classes
import com.example.dao.Registration
import com.example.dao.Schedule
import com.example.internals.intCell
import com.example.internals.stringCell
import com.example.ui.class2.AddScheduleDialog
import com.example.ui.class2.ClassEntryDialog
import com.example.ui.class2.StudentPickerDialog
import com.example.ui.course.CourseSchemaDialog
import com.example.ui.lecturer.LecturerSchemaDialog
import com.example.ui.student.StudentSchemaDialog
import javafx.application.Platform
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.ButtonType.CLOSE
import javafx.scene.control.ButtonType.NO
import javafx.scene.control.ButtonType.YES
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import javafx.scene.input.KeyCode.A
import javafx.scene.input.KeyCode.DIGIT1
import javafx.scene.input.KeyCode.DIGIT2
import javafx.scene.input.KeyCode.DIGIT3
import javafx.scene.input.KeyCode.M
import javafx.scene.input.KeyCode.Q
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.stage.Stage
import ktfx.bindings.bindingOf
import ktfx.bindings.booleanBindingOf
import ktfx.collections.emptyObservableList
import ktfx.collections.replaceAll
import ktfx.collections.toMutableObservableList
import ktfx.controls.columns
import ktfx.controls.constrained
import ktfx.controls.isNotSelected
import ktfx.coroutines.onAction
import ktfx.dialogs.confirmAlert
import ktfx.dialogs.errorAlert
import ktfx.inputs.plus
import ktfx.layouts.contextMenu
import ktfx.layouts.menu
import ktfx.layouts.menuBar
import ktfx.layouts.menuItem
import ktfx.layouts.scene
import ktfx.layouts.separatorMenuItem
import ktfx.layouts.splitPane
import ktfx.layouts.tableView
import ktfx.layouts.vbox
import ktfx.runLater
import ktfx.windows.minSize
import ktfx.windows.size2
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.DayOfWeek

class MainStage : Stage() {
    private companion object {
        val MIN_SIZE_STAGE = 800.0 to 600.0
    }

    lateinit var classTable: TableView<Class>
    lateinit var classAddMenu: MenuItem

    lateinit var classRegistrationTable: TableView<Registration>
    lateinit var classRegistrationAddMenu: MenuItem

    lateinit var classScheduleTable: TableView<Schedule>
    lateinit var classScheduleAddMenu: MenuItem

    init {
        title = "Homework 1.1"
        minSize = MIN_SIZE_STAGE
        size2 = MIN_SIZE_STAGE
        scene {
            vbox {
                menuBar {
                    isUseSystemMenuBar = IS_OS_MAC_OSX
                    "File" {
                        "Quit" {
                            accelerator = SHORTCUT_DOWN + Q
                            onAction { Platform.exit() }
                        }
                    }
                    "Edit" {
                        "Add Class" {
                            accelerator = SHORTCUT_DOWN + A
                            runLater {
                                onActionProperty().bind(classAddMenu.onActionProperty())
                            }
                        }
                        separatorMenuItem()
                        "Add Student to Class" {
                            runLater {
                                disableProperty().bind(classRegistrationAddMenu.disableProperty())
                                onActionProperty().bind(classRegistrationAddMenu.onActionProperty())
                            }
                        }
                        "Add Schedule to Class" {
                            runLater {
                                disableProperty().bind(classScheduleAddMenu.disableProperty())
                                onActionProperty().bind(classScheduleAddMenu.onActionProperty())
                            }
                        }
                    }
                    "View" {
                        "Courses" {
                            accelerator = SHORTCUT_DOWN + DIGIT1
                            onAction { CourseSchemaDialog().showAndWait() }
                        }
                        separatorMenuItem()
                        "Lecturers" {
                            accelerator = SHORTCUT_DOWN + DIGIT2
                            onAction { LecturerSchemaDialog().showAndWait() }
                        }
                        "Students" {
                            accelerator = SHORTCUT_DOWN + DIGIT3
                            onAction { StudentSchemaDialog().showAndWait() }
                        }
                    }
                    "Window" {
                        "Minimize" {
                            accelerator = SHORTCUT_DOWN + M
                            onAction { isIconified = true }
                        }
                        "Zoom" {
                            onAction { isMaximized = true }
                        }
                    }
                    "Help" {
                        "About" {
                            onAction { AboutDialog().show() }
                        }
                    }
                }
                splitPane {
                    classTable = tableView(
                        transaction {
                            Class.all().toMutableObservableList()
                        }
                    ) {
                        constrained()
                        columns {
                            append<Int>("Class ID").intCell { id.value }
                            append<String>("Course").stringCell {
                                transaction { course }.toString()
                            }
                            append<String>("Lecturer").stringCell {
                                transaction { lecturer }.toString()
                            }
                            append<String>("Initial date").stringCell { dateInitial.toString() }
                            append<String>("Final date").stringCell { dateFinal.toString() }
                        }
                        contextMenu {
                            classAddMenu = "Add" {
                                onAction {
                                    ClassEntryDialog().showAndWait().ifPresent {
                                        this@tableView.items += it
                                    }
                                }
                            }
                            "Edit" {
                                disableProperty().bind(
                                    this@tableView.selectionModel.selectedItemProperty().isNull
                                )
                                onAction {
                                    val oldVal = this@tableView.selectionModel.selectedItem
                                    ClassEntryDialog(oldVal).showAndWait()
                                        .ifPresent { this@tableView.items.replaceAll(oldVal, it) }
                                }
                            }
                            separatorMenuItem()
                            "Delete" {
                                disableProperty().bind(
                                    this@tableView.selectionModel.selectedItemProperty().isNull
                                )
                                onAction {
                                    transaction {
                                        this@tableView.selectionModel.selectedItem.delete()
                                        this@tableView.items -=
                                            this@tableView.selectionModel.selectedItem
                                    }
                                }
                            }
                            "Clear" {
                                disableProperty().bind(
                                    booleanBindingOf(this@tableView.items) {
                                        this@tableView.items.isEmpty()
                                    }
                                )
                                onAction {
                                    confirmAlert("Remove all classes?", NO, YES).ifPresent {
                                        if (it == YES) {
                                            transaction { Classes.deleteAll() }
                                            this@tableView.items.clear()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    splitPane {
                        orientation = VERTICAL
                        classRegistrationTable = tableView {
                            itemsProperty().bind(
                                bindingOf(
                                    classTable.selectionModel.selectedItemProperty()
                                ) {
                                    if (classTable.selectionModel.isNotSelected()) {
                                        emptyObservableList()
                                    } else {
                                        transaction {
                                            classTable.selectionModel.selectedItem.classStudents
                                                .toMutableObservableList()
                                        }
                                    }
                                }
                            )
                            constrained()
                            columns {
                                append<String>("Student").stringCell {
                                    transaction { student }.toString()
                                }
                                append<String>("Grade").stringCell { grade }
                            }
                            contextMenu {
                                classRegistrationAddMenu = "Add" {
                                    disableProperty().bind(
                                        classTable.selectionModel.selectedItemProperty().isNull
                                    )
                                    onAction {
                                        StudentPickerDialog().showAndWait().ifPresent { s ->
                                            if (classRegistrationTable.items
                                                    .any { transaction { it.student.id } == s.id }
                                            ) {
                                                errorAlert(
                                                    "The student is already in this class.",
                                                    CLOSE
                                                )
                                            } else {
                                                classRegistrationTable.items += transaction {
                                                    Registration.new {
                                                        `class` =
                                                            classTable.selectionModel.selectedItem
                                                        student = s
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                menu("Set Grade") {
                                    disableProperty().bind(
                                        this@tableView.selectionModel.selectedItemProperty().isNull
                                    )
                                    addSetGradeMenu("A")
                                    addSetGradeMenu("B")
                                    addSetGradeMenu("C")
                                    addSetGradeMenu("D")
                                    addSetGradeMenu("E")
                                    addSetGradeMenu("F")
                                    separatorMenuItem()
                                    addSetGradeMenu("Unset", null)
                                }
                                separatorMenuItem()
                                "Delete" {
                                    disableProperty().bind(
                                        this@tableView.selectionModel.selectedItemProperty().isNull
                                    )
                                    onAction {
                                        transaction {
                                            this@tableView.selectionModel.selectedItem.delete()
                                            this@tableView.items -=
                                                this@tableView.selectionModel.selectedItem
                                        }
                                    }
                                }
                            }
                        }
                        classScheduleTable = tableView {
                            itemsProperty().bind(
                                bindingOf(
                                    classTable.selectionModel.selectedItemProperty()
                                ) {
                                    if (classTable.selectionModel.isNotSelected()) {
                                        emptyObservableList()
                                    } else {
                                        transaction {
                                            classTable.selectionModel.selectedItem.schedules
                                                .toMutableObservableList()
                                        }
                                    }
                                }
                            )
                            constrained()
                            columns {
                                append<String>("Schedule").stringCell {
                                    DayOfWeek.of(day).toString()
                                }
                                append<String>("Start time").stringCell { timeStart.toString() }
                                append<String>("End time").stringCell { timeEnd.toString() }
                            }
                            contextMenu {
                                classScheduleAddMenu = "Add" {
                                    disableProperty().bind(
                                        classTable.selectionModel.selectedItemProperty().isNull
                                    )
                                    onAction {
                                        AddScheduleDialog(classTable.selectionModel.selectedItem)
                                            .showAndWait().ifPresent {
                                                this@tableView.items += it
                                            }
                                    }
                                }
                                separatorMenuItem()
                                "Delete" {
                                    disableProperty().bind(
                                        this@tableView.selectionModel.selectedItemProperty().isNull
                                    )
                                    onAction {
                                        transaction {
                                            this@tableView.selectionModel.selectedItem.delete()
                                            this@tableView.items -=
                                                this@tableView.selectionModel.selectedItem
                                        }
                                    }
                                }
                            }
                        }
                    }
                }.vgrow()
            }
        }
    }

    private fun Menu.addSetGradeMenu(menuText: String, gradeValue: String? = menuText) {
        items.add(
            menuItem(menuText) {
                onAction {
                    val registration = classRegistrationTable.selectionModel.selectedItem
                    classRegistrationTable.items.replaceAll(
                        registration,
                        transaction { registration.apply { grade = gradeValue } }
                    )
                }
            }
        )
    }
}
