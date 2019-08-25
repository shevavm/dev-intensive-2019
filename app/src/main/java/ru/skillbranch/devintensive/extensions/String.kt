package ru.skillbranch.devintensive.extensions

fun String.truncate(count : Int = 16) : String
{
    return if (this.trim().length > count)
        this.trim().dropLast(length - count).trim() + "..."
    else
        this.trim()
}

fun String.stripHtml() : String = this
    .replace("<[^>]*>".toRegex(), "")
    .replace("[ ]+".toRegex(), " ")