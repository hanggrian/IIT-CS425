package com.example

import com.example.ui.AboutDialog
import com.example.ui.MainStage
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.HPos
import javafx.scene.control.ButtonType.CLOSE
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode.Q
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.stage.Stage
import ktfx.controls.insetsOf
import ktfx.coroutines.onAction
import ktfx.dialogs.errorAlert
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
import ktfx.layouts.textArea
import ktfx.layouts.textField
import ktfx.layouts.vbox
import ktfx.runLater
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX
import org.ktorm.database.Database
import org.ktorm.logging.Slf4jLoggerAdapter
import java.util.prefs.Preferences

class App : Application() {
    private lateinit var db: Database
    private lateinit var prefs: Preferences

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
        prefs = Preferences.userNodeForPackage(App::class.java)
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
                    hostIpField = textField(prefs.get("host_ip", "127.0.0.1")) {
                        runLater { requestFocus() }
                        prefWidth = WIDTH_LONG
                        promptText = "IP Address"
                    }.grid(0, 1)
                    hostPortField = textField(prefs.get("host_port", "3306")) {
                        prefWidth = WIDTH_SHORT
                        promptText = "Port"
                    }.grid(0, 2)
                    label("Schema").grid(1, 0)
                    schemaField = textField(prefs.get("schema", "cta")) {
                        promptText = "Schema"
                    }.grid(1, 1 to 2)
                    label("User").grid(2, 0)
                    userField = textField(prefs.get("user", "root")) {
                        promptText = "User"
                    }.grid(2, 1 to 2)
                    label("Password").grid(3, 0)
                    passwordField = passwordField {
                        text = prefs.get("password", "")
                        promptText = "Password"
                    }.grid(3, 1 to 2)
                    keepSignCheck = checkBox("Keep me signed-in") {
                        isSelected = prefs.getBoolean("keep_sign", false)
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

        prefs.put("host_ip", hostIp)
        prefs.put("host_port", hostPort)
        prefs.put("schema", schema)
        prefs.put("user", user)
        prefs.put("password", password)
        prefs.putBoolean("keep_sign", keepSignCheck.isSelected)
        prefs.sync()
        prefs.flush()

        try {
            val db = Database.connect(
                "jdbc:mysql://$hostIp:$hostPort/$schema",
                driver = "com.mysql.cj.jdbc.Driver",
                user = user,
                password = password,
                logger = Slf4jLoggerAdapter("CTA")
            )
            MainStage(db).show()
            stage.close()
        } catch (e: Exception) {
            errorAlert("Connect Failed", null, "Expand dialog to show error details.", CLOSE) {
                dialogPane.expandableContent = textArea(e.message!!) {
                    isEditable = false
                }
            }
        }
    }
}
