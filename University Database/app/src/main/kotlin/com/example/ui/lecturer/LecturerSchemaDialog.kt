package com.example.ui.lecturer

import com.example.dao.Lecturer
import com.example.dao.Lecturers
import com.example.internals.SchemaDialog
import com.example.internals.intCell
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class LecturerSchemaDialog : SchemaDialog<Lecturer>("Lecturers", Lecturer, Lecturers) {
    init {
        table.columns {
            append<Int>("ID").intCell { id.value }
            append<String>("Name").stringCell { name }
            append<String>("Join date").stringCell { dateJoin.toString() }
        }
    }

    override fun onAdd() {
        LecturerEntryDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Lecturer) {
        LecturerEntryDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
