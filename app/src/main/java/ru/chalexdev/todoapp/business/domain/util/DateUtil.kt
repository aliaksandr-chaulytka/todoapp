package ru.chalexdev.todoapp.business.domain.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val dateFormat = SimpleDateFormat("EEE, MMM d, ''yy", Locale.ROOT)

    fun getCurrentTimestamp(): String {
        return dateFormat.format(Date())
    }
}