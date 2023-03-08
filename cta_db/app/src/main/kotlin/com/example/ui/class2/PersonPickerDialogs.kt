package com.example.ui.class2

import com.example.dao.Lecturer
import com.example.dao.Student
import com.example.internals.PickerDialog

class LecturerPickerDialog : PickerDialog<Lecturer>("Pick Lecturer", Lecturer) {
    override fun onSearch(item: Lecturer, text: String): Boolean =
        text in item.id.value.toString() ||
            text in item.name.lowercase()
}

class StudentPickerDialog : PickerDialog<Student>("Pick Student", Student) {
    override fun onSearch(item: Student, text: String): Boolean =
        text in item.id.value.toString() ||
            text in item.name.lowercase()
}
