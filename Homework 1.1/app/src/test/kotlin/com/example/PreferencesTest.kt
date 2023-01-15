package com.example

import java.util.prefs.Preferences
import kotlin.test.Test
import kotlin.test.assertEquals

/** Checks local preferences access. */

class PreferencesTest {

    @Test
    fun test() {
        val preferences = Preferences.userNodeForPackage(App::class.java)
        preferences.clear()

        assertEquals("default", preferences.get("test", "default"))

        preferences.put("test", "something")
        assertEquals("something", preferences.get("test", ""))
    }
}
