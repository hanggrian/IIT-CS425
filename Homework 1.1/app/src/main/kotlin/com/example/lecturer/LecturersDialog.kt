package com.example.lecturer

import com.example.dao.Lecturer
import com.example.dao.Lecturers
import com.example.internals.TableDialog
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class LecturersDialog : TableDialog<Lecturer>("Lecturers", Lecturer, Lecturers) {

    init {
        table.columns {
            append<String>("Name").stringCell { name }
            append<String>("Join Date").stringCell { dateJoin.toString() }
        }
    }

    override fun onAdd() {
        LecturerDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Lecturer) {
        LecturerDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
