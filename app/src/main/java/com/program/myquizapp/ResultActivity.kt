package com.program.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val textViewName = findViewById<TextView>(R.id.textViewUsername)
        val textViewScore = findViewById<TextView>(R.id.textViewScore)
        val buttonFinish = findViewById<Button>(R.id.buttonFinish)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWER, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val score = "Your Score is $correctAnswers out of $totalQuestions"

        textViewName.text = username
        textViewScore.text = score

        buttonFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}