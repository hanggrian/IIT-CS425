package com.example.ui.course

import com.example.dao.Course
import com.example.dao.Courses
import com.example.internals.SchemaDialog
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class CourseSchemaDialog : SchemaDialog<Course>("Courses", Course, Courses) {
    init {
        table.columns {
            append<String>("ID").stringCell { id.value }
            append<String>("Name").stringCell { name }
        }
    }

    override fun onAdd() {
        CourseEntryDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Course) {
        CourseEntryDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
