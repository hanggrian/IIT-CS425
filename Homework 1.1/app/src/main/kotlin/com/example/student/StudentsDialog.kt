package com.example.student

import com.example.dao.Student
import com.example.dao.Students
import com.example.internals.TableDialog
import com.example.internals.stringCell
import ktfx.collections.replaceAll
import ktfx.controls.columns

class StudentsDialog : TableDialog<Student>("Students", Student, Students) {

    init {
        table.columns {
            append<String>("Name").stringCell { name }
            append<String>("Join Date").stringCell { dateJoin.toString() }
            append<String>("Graduate Date").stringCell { dateGraduate.toString() }
        }
    }

    override fun onAdd() {
        StudentDialog().showAndWait().ifPresent { table.items += it }
    }

    override fun onEdit(item: Student) {
        StudentDialog(item).showAndWait().ifPresent { table.items.replaceAll(item, it) }
    }
}
