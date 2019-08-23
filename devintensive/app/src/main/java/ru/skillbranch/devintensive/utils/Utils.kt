package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?) : Pair<String?, String?>{

        if (fullName.isNullOrBlank())
            return Pair(null, null)

        val parts : List<String>? = fullName?.split(" ")

        val firstName : String? = parts?.getOrNull(0)
        val lastName : String? = parts?.getOrNull(1)

        return Pair(firstName, lastName)
    }

    fun transliteration(payload:String, divider:String=" ") =
         payload.replace(Regex("""[а-яА-Я ]""")) {
            when (it.value) {
                " " -> divider
                "а" -> "a" "А" -> "A"
                "б" -> "b" "Б" -> "B"
                "в" -> "v" "В" -> "V"
                "г" -> "g" "Г" -> "G"
                "д" -> "d" "Д" -> "D"
                "е" -> "e" "Е" -> "E"
                "ё" -> "e" "Ё" -> "E"
                "ж" -> "zh" "Ж" -> "Zh"
                "з" -> "z" "З" -> "Z"
                "и" -> "i" "И" -> "I"
                "й" -> "i" "Й" -> "I"
                "к" -> "k" "К" -> "K"
                "л" -> "l" "Л" -> "L"
                "м" -> "m" "М" -> "M"
                "н" -> "n" "Н" -> "N"
                "о" -> "o" "О" -> "O"
                "п" -> "p" "П" -> "P"
                "р" -> "r" "Р" -> "R"
                "с" -> "s" "С" -> "S"
                "т" -> "t" "Т" -> "T"
                "у" -> "u" "У" -> "U"
                "ф" -> "f" "Ф" -> "F"
                "х" -> "h" "Х" -> "H"
                "ц" -> "c" "Ц" -> "C"
                "ч" -> "ch" "Ч" -> "Ch"
                "ш" -> "sh" "Ш" -> "Sh"
                "щ" -> "sh'" "Щ" -> "Sh'"
                "ъ" -> "" "Ъ" -> ""
                "ы" -> "i" "Ы" -> "i"
                "ь" -> "" "Ь" -> ""
                "э" -> "e" "Э" -> "E"
                "ю" -> "yu" "Ю" -> "Yu"
                "я" -> "ya" "Я" -> "Ya"
                else -> it.value
            }
        }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null

        return "${firstName?.getOrNull(0) ?: ""}${lastName?.getOrNull(0) ?: ""}".toUpperCase()
    }
}