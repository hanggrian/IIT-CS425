package com.example

import com.example.course.CoursesDialog
import com.example.lecturer.LecturersDialog
import com.example.student.StudentsDialog
import javafx.application.Platform
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import ktfx.coroutines.onAction
import ktfx.inputs.plus
import ktfx.layouts.label
import ktfx.layouts.menuBar
import ktfx.layouts.scene
import ktfx.layouts.separatorMenuItem
import ktfx.layouts.splitPane
import ktfx.layouts.stackPane
import ktfx.layouts.vbox
import ktfx.windows.minSize
import org.apache.commons.lang3.SystemUtils

class MainStage : Stage() {
    private companion object {
        val MIN_SIZE_STAGE = 600.0 to 400.0
    }

    init {
        title = "Homework 1.1"
        minSize = MIN_SIZE_STAGE
        scene {
            vbox {
                menuBar {
                    isUseSystemMenuBar = SystemUtils.IS_OS_MAC_OSX
                    "File" {
                        "Quit" {
                            accelerator = KeyCombination.SHORTCUT_DOWN + KeyCode.Q
                            onAction { Platform.exit() }
                        }
                    }
                    "Edit" {
                        "Add Class" {
                        }
                    }
                    "View" {
                        "Courses" {
                            onAction { CoursesDialog().showAndWait() }
                        }
                        separatorMenuItem()
                        "Lecturers" {
                            onAction { LecturersDialog().showAndWait() }
                        }
                        "Students" {
                            onAction { StudentsDialog().showAndWait() }
                        }
                    }
                    "Window" {
                        "Minimize" {
                            accelerator = KeyCombination.SHORTCUT_DOWN + KeyCode.M
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
                    stackPane {
                        label("Classes")
                    }
                    splitPane {
                        orientation = VERTICAL
                        stackPane {
                            label("Students & Grade")
                        }
                        stackPane {
                            label("Schedules")
                        }
                    }
                }.vgrow()
            }
        }
    }
}
