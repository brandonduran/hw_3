package com.example.hw_3
import android.animation.Animator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.lang.Exception
import java.util.*
import android.animation.AnimatorListenerAdapter
import android.view.animation.Animation








class wordGame : AppCompatActivity() {
    var resultTextView: TextView? = null
    var timerTextView: TextView? = null
    lateinit var currentWord: TextView
    var scoreTextView: TextView? = null
    var linearLayout: LinearLayout? = null
    var gridLayout: GridLayout? = null
    var playAgain: Button? = null
    var button0: Button? = null
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var audio1: ImageView? = null
    var audio2: ImageView? = null
    var audio3: ImageView? = null
    var audio4: ImageView? = null
    var langIndicator = ""
    var currentLang  = arrayOf<String>()
    val phrases = arrayOf("Hello", "Good Morning", "Good Afternoon", "Thank You", "Please", "Ok", "How Are You", "Very Good", "Bye")
    val spanishList = arrayOf("Hola", "Buenos dias", "Buenas tardes", "Gracias", "Por favor", "Más o menos", "¿Cómo estás?", "Muy bien", "Adios")
    val frenchList = arrayOf("Salut", "Bonjour", "Bonsoir", "Merci ", "S'il vous plaît", "Pas mal", "Ça va?", "Très bien", "Au revoir")
    val italianList = arrayOf("Ciao", "Buongiorno", "Buona sera", "Grazie", "Per favore", "Non c'è male", "Come stai?", "Abbastanza bene", "Arrivederci")
    val audioNames = arrayOf("hello", "goodmorning", "goodafternoon", "thankyou", "please", "ok", "howareyou", "verygood", "bye")
    var wrong: kotlin.Int = 0
    var answers : Array<String?> = arrayOfNulls(4)
    var test : Array<Int?> = arrayOfNulls(4)
    var correct = 0
    var score = 0
    var totalProblems = 0
    var timerLength = 0
    var start = false
    var lang = ""
    var diff = 0 //easy problems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_game)
        val bundle: Bundle? = intent.extras
        lang = intent.getStringExtra("Lang").toString()
        Toast.makeText(applicationContext, lang, Toast.LENGTH_SHORT).show()
        diff = intent.getIntExtra("Diff", -1)
        resultTextView = findViewById(R.id.resultTextView)
        timerTextView = findViewById(R.id.timerTextView)
        currentWord = findViewById(R.id.currentWord)
        scoreTextView = findViewById(R.id.scoreTextView)
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        audio1 = findViewById(R.id.imageView0)
        audio2 = findViewById(R.id.imageView1)
        audio3 = findViewById(R.id.imageView2)
        audio4 = findViewById(R.id.imageView3)
        playAgain = findViewById(R.id.playAgain)
        linearLayout = findViewById(R.id.linearLayout)
        gridLayout = findViewById(R.id.gridLayout)
        resultTextView!!.text = ""
        playAgain!!.visibility = View.INVISIBLE
        questionTimer(diff)
        generateQuestion()
    }//onCreate

    fun playAudio(view: View) {
        val b = view as ImageView
        val sss = b.tag.toString().toInt()
        val word = audioNames[test[sss]!!]
        val resourceId =  resources.getIdentifier(word + langIndicator, "raw",packageName)
        val mediaPlayer = MediaPlayer.create(this, resourceId)
        mediaPlayer.start()
    }//playAudio

    fun questionTimer(difficulty: Int) {
        start = true;
        timerTextView!!.text = "30S"
        resultTextView!!.visibility = View.VISIBLE
        gridLayout!!.visibility = View.VISIBLE
        linearLayout!!.visibility = View.VISIBLE

        when (difficulty) {
            0 -> timerLength = 60100
            1 -> timerLength = 40100
            2 -> timerLength = 20100
        }//make harder difficulties shorter

        object : CountDownTimer(timerLength.toLong(), 1000) {
            override fun onTick(p0: Long) {
                timerTextView!!.text = (p0 / 1000).toString() + "S"
            }//onTick

            override fun onFinish() {
                resultTextView!!.text = "Done"
                timerTextView!!.text = "0S"
                Toast.makeText(
                    getApplicationContext(), "Your got " + score + " out of " +
                            totalProblems + " Correct", Toast.LENGTH_LONG
                ).show()
                start = false;
                resultTextView!!.visibility = View.INVISIBLE
                playAgain!!.visibility = View.VISIBLE
                gridLayout!!.visibility = View.INVISIBLE
                linearLayout!!.visibility = View.INVISIBLE
                audio1!!.visibility = View.INVISIBLE
                audio2!!.visibility = View.INVISIBLE
                audio3!!.visibility = View.INVISIBLE
                audio4!!.visibility = View.INVISIBLE
            }//onFinish
        }.start()//CountDown

    }//questionTimer

    fun spanish() {
        langIndicator = ""
        val rand = (0..8).random()
        currentWord.setText(phrases[rand])
        correct = (0..3).random()
        wrongAnswers(rand, 0, correct)
        answers[correct] = spanishList[rand]
        test[correct] = rand
    }//Spanish

    fun french() {
        langIndicator = "f"
        val rand = (0..8).random()
        currentWord.setText(phrases[rand])
        correct = (0..3).random()
        wrongAnswers(rand, 1, correct)
        answers[correct] = frenchList[rand]
        test[correct] = rand
    }//French

    fun italian() {
        langIndicator = "i"
        val rand = (0..8).random()
        currentWord.setText(phrases[rand])
        correct = (0..3).random()
        wrongAnswers(rand, 2, correct)
        answers[correct] = italianList[rand]
        test[correct] = rand
    }//Italian

    fun wrongAnswers(arrayIndex : Int, whichLang : Int, ansIndex : Int) {

        when (whichLang) {
            0 -> currentLang = spanishList.copyOf()
            1 -> currentLang = frenchList.copyOf()
            2 -> currentLang = italianList.copyOf()
        }
        for (count in 0..3) {
            if (count == ansIndex) {
                continue
            }//slot is filled by correct answer
            wrong = (0..8).random()
            while (wrong == arrayIndex || test.contains(wrong) ) {
                wrong = (0..8).random()
            }//keep generating random number to get a unique phrase from list
            answers[count] = currentLang[wrong]
            test[count] = wrong
        }
    }//wrongAnswers

    fun generateQuestion() {
        when (lang) {
            "Spanish" -> spanish()
            "French" -> french()
            else -> italian()
        }
        button0!!.text = answers[0]
        button1!!.text = answers[1]
        button2!!.text = answers[2]
        button3!!.text = answers[3]
    } //generateQuestion

    fun chooseAnswer(view: View) {
        if (start) {
            totalProblems++
            if (view.tag.toString() == Integer.toString(correct)) {
                score++
                resultTextView!!.text = "Correct"
            } else {
                resultTextView!!.text = "Wrong"
            }
            scoreTextView!!.text = "$score/$totalProblems"
            generateQuestion()
        }
    }//chooseAnswer

    fun playAgain(view: View) {
        score = 0
        totalProblems = 0
        start = true
        scoreTextView!!.text = "0/0"
        resultTextView!!.text = ""
        playAgain!!.visibility = View.INVISIBLE
        audio1!!.visibility = View.VISIBLE
        audio2!!.visibility = View.VISIBLE
        audio3!!.visibility = View.VISIBLE
        audio4!!.visibility = View.VISIBLE
        generateQuestion()
        questionTimer(diff)
    }//playAgain

}//wordGame