package com.example.hw_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    var language = "Spanish"
    lateinit var button : Button
    lateinit var flag : ImageView
    var checked : RadioButton? = null
    var difficulty = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        button = findViewById(R.id.button)
        flag = findViewById(R.id.imageView)
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    fun playGame(view: View?) {
        val intent = Intent(this, wordGame::class.java)
        intent.putExtra("Lang", language)
        intent.putExtra("Diff", difficulty)
        startActivity(intent)
    }//playGame

    fun howHard(view : View) {
        checked = (view as RadioButton)
        when(view.getId()) {
            R.id.easy -> difficulty = 0
            R.id.medium -> difficulty = 1
            R.id.hard -> difficulty = 2
        }
    }//howHard



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Spanish -> {
                language = "Spanish"
                button.text = "Empieza"
                flag.setImageResource(R.drawable.spain)
                flag.isVisible = true
            }
            R.id.French -> {
                language = "French"
                button.text = "Allé"
                flag.setImageResource(R.drawable.france)
                flag.isVisible = true
            }
            R.id.Italian -> {
                language = "Italian"
                button.text = "Andare"
                flag.setImageResource(R.drawable.italy)
                flag.isVisible = true
            }
        }
        return true
    } //onOptionsItemSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("MMLKDS:LKFJDSKL:f")
    }//onCreate
}//MainActivity