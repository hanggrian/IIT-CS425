package com.example.ui.student

import com.example.dao.Student
import com.example.dao.Students
import com.example.internals.SchemaDialog
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class StudentSchemaDialog : SchemaDialog<Student>("Students", Student, Students) {
    init {
        table.columns {
            append<String>("Student").stringCell { toString() }
            append<String>("Join date").stringCell { dateJoin.toString() }
            append<String>("Graduate date").stringCell { dateGraduate?.toString() }
        }
    }

    override fun onAdd() {
        StudentEntryDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Student) {
        StudentEntryDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
