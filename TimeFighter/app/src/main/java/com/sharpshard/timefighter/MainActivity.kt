package com.sharpshard.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() { //class - inheritance

    internal lateinit var tapMeButton: Button  //var_name: what it is
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView

    internal var score = 0

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 30000 // Long is an int can contain a long number
    internal val CountDownInterval: Long = 1000

    companion object {
        private  val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) { //def method fun - function, override inherited method, onCreate is entry point, constructor?
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // R - resource file, activity resource location, set contentview to be file

        Log.d(TAG, "onCreate called. Score is: $score")

        tapMeButton = findViewById(R.id.tapMeButton)  // have to come after setContentView pointed to acitivty_main
        gameScoreTextView = findViewById(R.id.gameScoreTextView)
        timeLeftTextView = findViewById(R.id.timeLeftTextView)

        tapMeButton.setOnClickListener {view ->
            val bounceAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore()
        }

        resetGame()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionaAbout){
            showinfo()
        }
        return true
    }

    private fun showinfo() {

    }

    private fun resetGame() {
        score = 0

        gameScoreTextView.text = getString(R.string.gameScore, score)

        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.timeLeft, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, CountDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.timeLeft, timeLeft)
            }

            override fun onFinish(){
                endGame()
            }
        }

        gameStarted = false
    }

    private fun incrementScore() {
        if(!gameStarted) {
            startGame()
        }

        score += 1
        val newScore = getString(R.string.gameScore, score)
        gameScoreTextView.text = newScore
    }

    private  fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private  fun endGame() {
        Toast.makeText(applicationContext, getString(R.string.gameOverMessage, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}