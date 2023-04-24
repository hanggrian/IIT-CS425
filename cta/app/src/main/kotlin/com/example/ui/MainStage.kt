package com.example.ui

import com.example.App
import com.example.dao.Alert
import com.example.dao.Station
import com.example.dao.Trip
import com.example.dao.alerts
import com.example.dao.stations
import com.example.dao.trips
import com.example.string
import com.example.to
import com.example.ui.alert.AddAlertDialog
import com.example.ui.alert.ViewAlertDialog
import com.example.ui.conductor.ConductorsDialog
import com.example.ui.passenger.PassengersDialog
import com.example.until
import javafx.application.Platform
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import javafx.scene.input.KeyCode.DIGIT2
import javafx.scene.input.KeyCode.DIGIT3
import javafx.scene.input.KeyCode.M
import javafx.scene.input.KeyCode.Q
import javafx.scene.input.KeyCode.R
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.stage.Stage
import ktfx.collections.mutableObservableListOf
import ktfx.controls.columns
import ktfx.controls.constrained
import ktfx.coroutines.onAction
import ktfx.dialogs.infoAlert
import ktfx.inputs.isDoubleClick
import ktfx.inputs.plus
import ktfx.layouts.contextMenu
import ktfx.layouts.menuBar
import ktfx.layouts.scene
import ktfx.layouts.separatorMenuItem
import ktfx.layouts.splitPane
import ktfx.layouts.tableView
import ktfx.layouts.vbox
import ktfx.listeners.onMouseClicked
import ktfx.runLater
import ktfx.windows.minSize
import ktfx.windows.size2
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX
import org.ktorm.database.Database
import org.ktorm.entity.toCollection
import java.util.prefs.Preferences

class MainStage(db: Database) : Stage() {
    private companion object {
        val MIN_SIZE_STAGE = 1000.0 to 500.0
    }

    lateinit var tripTable: TableView<Trip>
    lateinit var alertTable: TableView<Alert>
    lateinit var alertAddMenu: MenuItem
    lateinit var stationTable: TableView<Station>
    lateinit var stationAddMenu: MenuItem

    init {
        title = "Chicago Transit Authority (CTA)"
        minSize = MIN_SIZE_STAGE
        size2 = MIN_SIZE_STAGE
        scene {
            vbox {
                menuBar {
                    isUseSystemMenuBar = IS_OS_MAC_OSX
                    "File" {
                        "Refresh" {
                            accelerator = SHORTCUT_DOWN + R
                            onAction {
                                tripTable.items = db.trips.toCollection(mutableObservableListOf())
                                alertTable.items = db.alerts.toCollection(mutableObservableListOf())
                                stationTable.items =
                                    db.stations.toCollection(mutableObservableListOf())
                            }
                        }
                        separatorMenuItem()
                        "Logout" {
                            onAction {
                                Preferences.userNodeForPackage(App::class.java).run {
                                    clear()
                                    sync()
                                    flush()
                                }
                                Platform.exit()
                            }
                        }
                        "Quit" {
                            accelerator = SHORTCUT_DOWN + Q
                            onAction { Platform.exit() }
                        }
                    }
                    "Edit" {
                        "Add Alert" {
                            runLater {
                                disableProperty().bind(alertAddMenu.disableProperty())
                                onActionProperty().bind(alertAddMenu.onActionProperty())
                            }
                        }
                    }
                    "View" {
                        "Conductors" {
                            accelerator = SHORTCUT_DOWN + DIGIT2
                            onAction { ConductorsDialog(db).showAndWait() }
                        }
                        "Passengers" {
                            accelerator = SHORTCUT_DOWN + DIGIT3
                            onAction { PassengersDialog(db).showAndWait() }
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
                    tripTable = tableView(db.trips.toCollection(mutableObservableListOf())) {
                        constrained()
                        columns {
                            append<String>("Trip").string {
                                "$tripTimestamp - ${passenger.fullname} " +
                                    "(${passenger.passengerId})"
                            }
                            append<String>("Fare").string {
                                buildString {
                                    append(fare)
                                    if (pass != null) {
                                        append(" (Pass)")
                                    }
                                }
                            }
                            append<String>("Track").string { track.trackColor }
                            append<String>("Station").string { stationNameIn to stationNameOut }
                        }
                    }
                    splitPane {
                        orientation = VERTICAL
                        alertTable = tableView(db.alerts.toCollection(mutableObservableListOf())) {
                            constrained()
                            columns {
                                append<String>("Alert").string { title }
                                append<String>("Date")
                                    .string { dateStart.toString() until dateEnd?.toString() }
                            }
                            onMouseClicked { event ->
                                if (event.isDoubleClick()) {
                                    alertTable.selectionModel.selectedItem
                                        ?.let { ViewAlertDialog(it).showAndWait() }
                                }
                            }
                            contextMenu {
                                alertAddMenu = "Add" {
                                    onAction {
                                        AddAlertDialog(db).showAndWait().ifPresent {
                                            alertTable.items += it
                                            infoAlert("Alert added.")
                                        }
                                    }
                                }
                            }
                        }
                        stationTable =
                            tableView(db.stations.toCollection(mutableObservableListOf())) {
                                constrained()
                                columns {
                                    append<String>("Station")
                                        .string { "$stationName (${track.trackColor})" }
                                    append<String>("Address").string { "$location, $zip" }
                                    append<String>("Features").string {
                                        buildString {
                                            if (hasElevator) append("Elevator, ")
                                            if (hasParking) append("Parking, ")
                                        }.substringBeforeLast(',')
                                    }
                                }
                            }
                    }
                }.vgrow()
            }
        }
    }
}
