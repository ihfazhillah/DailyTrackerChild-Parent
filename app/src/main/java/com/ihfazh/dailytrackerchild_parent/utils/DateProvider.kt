package com.ihfazh.dailytrackerchild_parent.utils

import android.icu.util.Calendar
import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.HijriDateItem

class DateProvider {
    private val islamicMonths = listOf(
        "Muharram",
        "Shofar",
        "Robi'ul Awwal",
        "Robi'uts Tsani",
        "Jumadil Ula",
        "Jumadits Tsaniah",
        "Rojab",
        "Sya'ban",
        "Romadhon",
        "Syawwal",
        "Dzul Qo'dah",
        "Dzul Hijjah"
    )

    private val georgianMonths = listOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    fun getDateItem(): DateItem {
        return DateItem(
            getHijriDateItem(),
            getGeorgianDateString()
        )
    }

    private fun getHijriDateItem(): HijriDateItem {
        val date = IslamicCalendar.getInstance(ULocale("@calendar=islamic-umalqura"))

        val day = date.get(IslamicCalendar.DAY_OF_MONTH)
        val month = date.get(IslamicCalendar.MONTH)
        val year = date.get(IslamicCalendar.YEAR)

        return HijriDateItem(day, islamicMonths[month], year)
    }

    private fun getGeorgianDateString(): String {
        val date = Calendar.getInstance()
        val day = date.get(Calendar.DAY_OF_MONTH)
        val month = date.get(Calendar.MONTH)
        val year = date.get(Calendar.YEAR)
        return "$day ${georgianMonths[month]} $year"
    }
}