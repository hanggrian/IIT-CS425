package com.example

import java.util.prefs.Preferences
import kotlin.test.Test
import kotlin.test.assertEquals

/** Checks local preferences access. */
class PreferencesTest {
    @Test
    fun test() {
        val prefs = Preferences.userNodeForPackage(App::class.java)
        prefs.clear()

        assertEquals("default", prefs.get("test", "default"))

        prefs.put("test", "something")
        assertEquals("something", prefs.get("test", ""))
    }
}
