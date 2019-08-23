package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units:TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time

    time+=when (units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date:Date = Date()) =
     when (val diff = this.time - date.time) {
        in -1 * SECOND .. 1 * SECOND ->  "только что"
        in 1 * SECOND .. 45 * SECOND -> "несколько секунд вперед"
        in -45 * SECOND .. -1 * SECOND -> "несколько секунд назад"
        in 45 * SECOND .. 75 * SECOND -> "минуту вперед"
        in -75 * SECOND .. -45 * SECOND -> "минуту назад"
        in 75 * SECOND .. 45 * MINUTE -> "через ${TimeUnits.MINUTE.plural((diff /MINUTE).toInt())}"
        in -45 * MINUTE .. -75 * SECOND -> "${TimeUnits.MINUTE.plural((diff /MINUTE).toInt())} назад"
        in 45 * MINUTE .. 75 * MINUTE -> "час вперед"
        in -75 * MINUTE .. -45 * MINUTE -> "час назад"
        in 75 * MINUTE .. 22 * HOUR -> "через ${TimeUnits.HOUR.plural((diff /HOUR).toInt())}"
        in -22 * HOUR .. -75 * MINUTE-> "${TimeUnits.HOUR.plural((diff /HOUR).toInt())} назад"
        in 22 * HOUR .. 26 * HOUR -> "день вперед"
        in -26 * HOUR .. -22 * HOUR -> "день назад"
        in 26 * HOUR .. 360 * DAY -> "через ${TimeUnits.DAY.plural((diff / DAY).toInt())}"
        in -360 * DAY .. -26 * HOUR -> "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад"
        in Long.MIN_VALUE .. -360 * DAY -> "более года назад"
        else -> "более чем через год"
    }

enum class TimeUnits{
    SECOND {
        override fun plural(value:Int) = plural_common(value, "секунд", "секунду", "секунды")
    },
    MINUTE {
        override fun plural(value:Int) = plural_common(value, "минут", "минуту", "минуты")
    },
    HOUR {
        override fun plural(value:Int) = plural_common(value, "часов", "час", "часа")
    },
    DAY {
        override fun plural(value:Int) = plural_common(value, "дней", "день", "дня")//"${abs(value)} ${if (abs(value) == 1) "день" else if (abs(value) % 10 <= 4 ) "дня" else "дней"}"
    };

    abstract fun plural(value:Int): String
    protected fun plural_common(value:Int, strDef : String, str1 : String, str2_4 : String) : String  {
        val a = abs(value)
        val m = a % 100
        if (m in 11..20)
            return "$a $strDef"
        else if (a % 10 == 1)
            return "$a $str1"
        else if (a % 10 <= 4 && a % 10 != 0)
            return "$a $str2_4"
        return "$a $strDef"
    }
}