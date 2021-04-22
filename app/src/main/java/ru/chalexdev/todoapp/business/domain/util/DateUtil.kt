package ru.chalexdev.todoapp.business.domain.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil(private val dateFormat: SimpleDateFormat) {

    fun getCurrentTimestamp(): String {
        return dateFormat.format(Date())
    }
}