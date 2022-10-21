package com.example.physicsquiz

import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class QuizState (val points: Int, val correct: Int, val frauds: Int, val correct_answer: Boolean, val question_number: Int) : Parcelable

class QuestionsActivity : AppCompatActivity() {

    class Question(
        val question: String,
        val correctAnswer: Boolean,
        val url: String?,
    )

    private val listOfQuestions = listOf(
        Question("Czy fizyka, astronomia i ISSP to kierunki studiów na WFiA?",  true, "https://wfa.uni.wroc.pl/pl/"),
        Question("Czy dziekanat WFiA jest otwarty w środy?", false, "https://wfa.uni.wroc.pl/info/79?"),
        Question("Czy WFiA znajduje się przy ulicy plac Maxa Borna 1 ?",  false, "https://www.google.com/search?q=wfia+uwr&rlz=1C5CHFA_enPL1027PL1027&oq=wfia+uwr&aqs=chrome..69i57j46i19i175i199i512j35i39j0i19i22i30l2.1152j0j7&sourceid=chrome&ie=UTF-8"),
        Question("Czy na WFiA istnieje Koło Naukowe Fizyków Teoretyków?", true, "https://wfa.uni.wroc.pl/info/105?"),
        Question("Czy Wrocław jest stolicą Polski?", false, "https://pl.wikipedia.org/wiki/Stolica_Polski"),
        Question("Czy na V semestrze ISSP Programowanie urządzeń mobilnych to kurs obowiązkowy?", true, "https://wfa.uni.wroc.pl/info/pub/content/5469/files/Informatyka%20stosowana%20i%20systemy%20pomiarowe%202022_2023.pdf"),
        Question("Czy chcesz wygrać ten quiz?", true, "https://upload.wikimedia.org/wikipedia/commons/d/dc/YES-Snowboards-logo.jpg"),
        Question("Czy jabłko to owoc?", true, "https://pl.wikipedia.org/wiki/Jab%C5%82ko"),
        Question("Czy Wrocław leży nad morzem?", false, "https://pl.wikipedia.org/wiki/Wroc%C5%82aw"),
        Question("Czy to jest pytanie numer 10?", true, "https://upload.wikimedia.org/wikipedia/commons/d/dc/YES-Snowboards-logo.jpg"),
    )

    class Statistics(
        var points: Int,
        var correct: Int,
        var frauds: Int,
    )
    private val gameStats = Statistics(0, 0, 0)

    private val questionNumberText: TextView by lazy { findViewById(R.id.question_number_text) }
    private val questionContentText: TextView by lazy { findViewById(R.id.question_content_text) }

    fun answerCheat(view: View) {
        gameStats.frauds += 1
        gameStats.points -= 15
        val fraudIntent = Intent(this, FraudActivity::class.java)
            .putExtra("quiz_data", QuizState(gameStats.points, gameStats.correct, gameStats.frauds, listOfQuestions[questionNumber - 1].correctAnswer, questionNumber))
        startActivity(fraudIntent)
    }

    private fun setQuestion(){
        currentQuestion = listOfQuestions[questionNumber-1]
        questionNumberText.text = "Question $questionNumber"
        questionContentText.text = currentQuestion.question
    }

    private fun answerQuestion(answer: Boolean){
        if (currentQuestion.correctAnswer == answer){
            gameStats.correct += 1
            gameStats.points += 10
        }
        questionNumber += 1
        if(questionNumber <= listOfQuestions.size){
            setQuestion()
        }
    }

    private var questionNumber: Int = 1
    private var currentQuestion: Question = listOfQuestions[questionNumber-1]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        findViewById<Button>(R.id.button_find_answer).setOnClickListener{
            val redirectIntent = Intent(Intent.ACTION_VIEW, Uri.parse(listOfQuestions[questionNumber - 1].url)).apply {
                addCategory(CATEGORY_BROWSABLE)
            }
            if (redirectIntent.resolveActivity(packageManager) != null)
                startActivity(redirectIntent)
        }
        val quizData = intent.getParcelableExtra<QuizState>("quiz_data")
        if(quizData != null){
            questionNumber = quizData.question_number
            currentQuestion = listOfQuestions[questionNumber - 1]
            gameStats.points = quizData.points
            gameStats.frauds = quizData.frauds
            gameStats.correct = quizData.correct
        }
        setQuestion()
    }

    fun answerYes(view: View) {
        answerQuestion(true)
        if(questionNumber > listOfQuestions.size){
            val overviewIntent = Intent(this, OverviewActivity::class.java)
                .putExtra("quiz_data", QuizState(gameStats.points, gameStats.correct, gameStats.frauds, listOfQuestions[questionNumber - 2].correctAnswer, questionNumber))
            startActivity(overviewIntent)
        }
    }

    fun answerNo(view: View) {
        answerQuestion(false)
        if(questionNumber > listOfQuestions.size){
            val overviewIntent = Intent(this, OverviewActivity::class.java)
                .putExtra("quiz_data", QuizState(gameStats.points, gameStats.correct, gameStats.frauds, listOfQuestions[questionNumber - 2].correctAnswer, questionNumber))
            startActivity(overviewIntent)
        }

    }
}