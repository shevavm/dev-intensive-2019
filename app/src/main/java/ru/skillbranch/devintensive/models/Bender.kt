package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> = when {
        question == Question.IDLE -> question.question to status.color
        !question.validation(answer).first -> "${question.validation(answer).second}\n${question.question}" to status.color
        question.answers.contains(answer.toLowerCase().trim()) -> {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        }
        else -> {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status = values()[ordinal + 1]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion() = values()[ordinal + 1]
            override fun validation(answer: String?): Pair<Boolean, String> =
                (!answer.isNullOrBlank() && answer.first().isUpperCase()) to "Имя должно начинаться с заглавной буквы"
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion() = values()[ordinal + 1]
            override fun validation(answer: String?): Pair<Boolean, String> =
                (!answer.isNullOrBlank() && answer.first().isLowerCase()) to "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion() = values()[ordinal + 1]
            override fun validation(answer: String?): Pair<Boolean, String> =
                (answer?.all { it.isLetter() } ?: false) to "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion() = values()[ordinal + 1]
            override fun validation(answer: String?): Pair<Boolean, String> =
                (answer?.all { it.isDigit() } ?: false) to "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion() = values()[ordinal + 1]
            override fun validation(answer: String?): Pair<Boolean, String> =
                (answer?.length == 7 && answer.all { it.isDigit() }) to "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion() = this
            override fun validation(answer: String?): Pair<Boolean, String> = true to ""
        };

        abstract fun nextQuestion(): Question
        abstract fun validation(answer: String?): Pair<Boolean, String>
    }
}