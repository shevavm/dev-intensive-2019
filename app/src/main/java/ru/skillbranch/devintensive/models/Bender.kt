
import ru.skillbranch.devintensive.extensions.isFirstLower
import ru.skillbranch.devintensive.extensions.isFirstUpper
import ru.skillbranch.devintensive.extensions.isNoDigits
import ru.skillbranch.devintensive.extensions.isOnlyDigits

class Bender (var status:Status = Status.NORMAL, var question:Question = Question.NAME) {

    private var missCounter = 0
    fun getCounter(): Int {
        return missCounter
    }

    fun setCounter(counter: Int) {
        this.missCounter = counter
    }

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val str = validateAnswer(question, answer)
        if (str != null) {
            return "$str\n${question.question}" to status.color
        }
        return if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            if (missCounter == 3) {
                missCounter = 0
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                missCounter++
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

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "Bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question

    }

    private fun validateAnswer(question: Question, answer: String): String? {
        return when (question) {
            Question.NAME -> {
                val c: String = answer.substring(0, 1)
                if (c.isFirstUpper()) {
                    null
                } else {
                    "Имя должно начинаться с заглавной буквы"
                }
            }
            Question.PROFESSION -> {
                val c: String = answer.substring(0, 1)
                if (c.isFirstLower()) {
                    null
                } else {
                    "Профессия должна начинаться со строчной буквы"
                }
            }
            Question.MATERIAL -> {
                if (answer.isNoDigits()) {
                    null
                } else {
                    "Материал не должен содержать цифр"
                }
            }
            Question.BDAY -> {
                if (answer.isOnlyDigits()) {
                    null
                } else {
                    "Год моего рождения должен содержать только цифры"
                }
            }
            Question.SERIAL -> {
                if (answer.length == 7 && answer.isOnlyDigits()) {
                    null
                } else {
                    "Серийный номер содержит только цифры, и их 7"
                }
            }
            Question.IDLE -> "Отлично - ты справился"
        }
    }
}
