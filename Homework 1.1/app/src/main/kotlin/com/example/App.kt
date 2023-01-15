package com.example

import com.example.dao.Classes
import com.example.dao.Courses
import com.example.dao.Lecturers
import com.example.dao.Registrations
import com.example.dao.Schedules
import com.example.dao.Students
import com.example.ui.AboutDialog
import com.example.ui.MainStage
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.HPos
import javafx.scene.control.CheckBox
import javafx.scene.control.Dialog
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode.Q
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.stage.Stage
import ktfx.controls.insetsOf
import ktfx.coroutines.onAction
import ktfx.dialogs.buttons
import ktfx.dialogs.headerTitle
import ktfx.inputs.plus
import ktfx.launchApplication
import ktfx.layouts.button
import ktfx.layouts.buttonBar
import ktfx.layouts.checkBox
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.menuBar
import ktfx.layouts.menuItem
import ktfx.layouts.passwordField
import ktfx.layouts.scene
import ktfx.layouts.textField
import ktfx.layouts.vbox
import ktfx.runLater
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.prefs.Preferences

class App : Application() {
    private lateinit var database: Database
    private lateinit var preferences: Preferences

    lateinit var stage: Stage
    lateinit var hostIpField: TextField
    lateinit var hostPortField: TextField
    lateinit var schemaField: TextField
    lateinit var userField: TextField
    lateinit var passwordField: TextField
    lateinit var keepSignCheck: CheckBox

    companion object {
        private const val WIDTH_SHORT = 60.0
        private const val WIDTH_LONG = 100.0

        @JvmStatic
        fun main(args: Array<String>): Unit = launchApplication<App>(*args)
    }

    override fun init() {
        preferences = Preferences.userNodeForPackage(App::class.java)
    }

    override fun start(stage: Stage) {
        this@App.stage = stage
        stage.title = "Login to MySQL"
        stage.isResizable = false
        stage.scene {
            vbox {
                menuBar {
                    isUseSystemMenuBar = IS_OS_MAC_OSX
                    "File" {
                        menuItem("Quit") {
                            accelerator = SHORTCUT_DOWN + Q
                            onAction { Platform.exit() }
                        }
                    }
                    "Help" {
                        menuItem("About") {
                            onAction {
                                AboutDialog().showAndWait()
                            }
                        }
                    }
                }
                gridPane {
                    padding = insetsOf(10)
                    hgap = 10.0
                    vgap = 10.0
                    label("Host").grid(0, 0)
                    hostIpField = textField(preferences.get("host_ip", "")) {
                        runLater { requestFocus() }
                        prefWidth = WIDTH_LONG
                        promptText = "IP Address"
                    }.grid(0, 1)
                    hostPortField = textField(preferences.get("host_port", "")) {
                        prefWidth = WIDTH_SHORT
                        promptText = "Port"
                    }.grid(0, 2)
                    label("Schema").grid(1, 0)
                    schemaField = textField(preferences.get("schema", "")) {
                        promptText = "Schema"
                    }.grid(1, 1 to 2)
                    label("User").grid(2, 0)
                    userField = textField(preferences.get("user", "")) {
                        promptText = "User"
                    }.grid(2, 1 to 2)
                    label("Password").grid(3, 0)
                    passwordField = passwordField {
                        text = preferences.get("password", "")
                        promptText = "Password"
                    }.grid(3, 1 to 2)
                    keepSignCheck = checkBox("Keep me signed-in") {
                        isSelected = preferences.getBoolean("keep_sign", false)
                    }.grid(4, 0 to 3).halign(HPos.RIGHT)
                    buttonBar {
                        button("Quit") {
                            isCancelButton = true
                            onAction { Platform.exit() }
                        }
                        button("Connect") {
                            isDefaultButton = true
                            onAction { connect() }
                        }
                    }.grid(5, 0 to 3)
                }
            }
        }
        stage.show()

        if (keepSignCheck.isSelected) {
            connect()
        }
    }

    private fun connect() {
        val hostIp = hostIpField.text
        val hostPort = hostPortField.text
        val schema = schemaField.text
        val user = userField.text
        val password = passwordField.text

        preferences.put("host_ip", hostIp)
        preferences.put("host_port", hostPort)
        preferences.put("schema", schema)
        preferences.put("user", user)
        preferences.put("password", password)
        preferences.putBoolean("keep_sign", keepSignCheck.isSelected)
        preferences.sync()
        preferences.flush()

        database = Database.connect(
            "jdbc:mysql://$hostIp:$hostPort/$schema",
            driver = "com.mysql.cj.jdbc.Driver",
            user = user,
            password = password
        )

        try {
            transaction {
                addLogger(Slf4jSqlDebugLogger)
                SchemaUtils.create(Classes, Registrations, Courses, Lecturers, Schedules, Students)
            }
            MainStage().show()
            stage.close()
        } catch (e: Exception) {
            Dialog<Unit>().apply {
                headerTitle = "Connect Failed"
                contentText = "Expand dialog to show error details."
                dialogPane.expandableContent = TextArea(e.message)
                buttons {
                    close()
                }
            }.showAndWait()
        }
    }
}
