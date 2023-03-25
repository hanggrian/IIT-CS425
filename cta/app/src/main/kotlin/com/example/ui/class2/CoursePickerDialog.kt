package com.example.ui.class2

import com.example.dao.Course
import com.example.internals.PickerDialog

class CoursePickerDialog : PickerDialog<Course>("Pick Course", Course) {
    override fun onSearch(item: Course, text: String): Boolean =
        text in item.id.value.lowercase() ||
            text in item.name.lowercase()
}
