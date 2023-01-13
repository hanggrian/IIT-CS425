package com.example.course

import com.example.dao.Course
import com.example.dao.Courses
import com.example.internals.TableDialog
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class CoursesDialog : TableDialog<Course>("Courses", Course, Courses) {

    init {
        table.columns {
            append<String>("Name").stringCell { name }
            append<String>("ID").stringCell { id.value }
        }
    }

    override fun onAdd() {
        CourseDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Course) {
        CourseDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
