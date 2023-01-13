package com.example.internals

import com.example.dao.Person
import javafx.scene.control.DatePicker
import ktfx.layouts.KtfxGridPane
import ktfx.layouts.datePicker
import ktfx.layouts.label
import java.time.LocalDate

abstract class PersonDialog<T : Person>(prefill: T?, title: String) :
    NamedDialog<T>(prefill, title) {
    val dateJoinPicker: DatePicker

    init {
        (dialogPane.content as KtfxGridPane).run {
            label("Join").grid(1, 0)
            dateJoinPicker = datePicker(prefill?.dateJoin ?: LocalDate.now()) {
                isDisable = true
            }.grid(1, 1)
        }
    }
}
