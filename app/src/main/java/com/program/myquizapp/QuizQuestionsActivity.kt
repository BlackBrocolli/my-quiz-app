package com.program.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition: Int = 1
    private lateinit var questionsList: ArrayList<Question>
    private var selectedOptionPosition: Int = 0
    private var isNextQuestionButtonEnabled: Boolean = false
    private var username: String? = null
    private var correctAnswers: Int = 0

    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgressBar: TextView
    private lateinit var textViewQuestion: TextView
    private lateinit var imageViewFlag: ImageView

    private lateinit var textViewOption1: TextView
    private lateinit var textViewOption2: TextView
    private lateinit var textViewOption3: TextView
    private lateinit var textViewOption4: TextView

    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        username = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        textViewProgressBar = findViewById(R.id.textViewProgress)
        textViewQuestion = findViewById(R.id.textViewQuestion)
        imageViewFlag = findViewById(R.id.imageViewFlag)
        textViewOption1 = findViewById(R.id.textViewOption1)
        textViewOption2 = findViewById(R.id.textViewOption2)
        textViewOption3 = findViewById(R.id.textViewOption3)
        textViewOption4 = findViewById(R.id.textViewOption4)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        textViewOption1.setOnClickListener(this)
        textViewOption2.setOnClickListener(this)
        textViewOption3.setOnClickListener(this)
        textViewOption4.setOnClickListener(this)
        buttonSubmit.setOnClickListener(this)

        questionsList = Constants.getQuestions()

        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionsView()

        val question: Question = questionsList[currentPosition - 1]

        progressBar.max = questionsList.size
        progressBar.progress = currentPosition
        textViewProgressBar.text = "$currentPosition/${progressBar.max}"

        textViewQuestion.text = question.question
        imageViewFlag.setImageResource(question.image)
        textViewOption1.text = question.optionOne
        textViewOption2.text = question.optionTwo
        textViewOption3.text = question.optionThree
        textViewOption4.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, textViewOption1)
        options.add(1, textViewOption2)
        options.add(2, textViewOption3)
        options.add(3, textViewOption4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOptionNum: Int) {
        defaultOptionsView()

        selectedOptionPosition = selectedOptionNum

        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.textViewOption1 -> {
                selectedOptionView(textViewOption1, 1)
            }

            R.id.textViewOption2 -> {
                selectedOptionView(textViewOption2, 2)
            }

            R.id.textViewOption3 -> {
                selectedOptionView(textViewOption3, 3)
            }

            R.id.textViewOption4 -> {
                selectedOptionView(textViewOption4, 4)
            }
            R.id.buttonSubmit -> {

                if (selectedOptionPosition != 0) {

                    if (isNextQuestionButtonEnabled) {
                        currentPosition++

                        when {
                            currentPosition <= questionsList.size -> {
                                setQuestion()
                            }
                            else -> {
                                val intent = Intent(this, ResultActivity::class.java)
                                intent.putExtra(Constants.USER_NAME, username)
                                intent.putExtra(Constants.CORRECT_ANSWER, correctAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList.size)
                                startActivity(intent)
                                finish()
                            }
                        }

                        buttonSubmit.text = "Submit"
                        selectedOptionPosition = 0
                        isNextQuestionButtonEnabled = false

                    } else {

                        val question = questionsList[currentPosition - 1]
                        if (question.correctAnswer != selectedOptionPosition) {
                            answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                        } else {
                            correctAnswers++
                        }
                        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                        if (currentPosition == questionsList.size) {
                            buttonSubmit.text = "FINISH"
                        } else {
                            buttonSubmit.text = "GO TO NEXT QUESTION"
                        }

                        isNextQuestionButtonEnabled = true
                    }

                } else {
                    Toast.makeText(this, "Please select your answer", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                textViewOption1.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2 -> {
                textViewOption2.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            3 -> {
                textViewOption3.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4 -> {
                textViewOption4.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}