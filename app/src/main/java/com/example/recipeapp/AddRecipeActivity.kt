package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddRecipeActivity : AppCompatActivity() {

    lateinit var titleInput : EditText
    lateinit var authorInput : EditText
    lateinit var ingrInput : EditText
    lateinit var instInput : EditText

    lateinit var submitButton : Button
    lateinit var cancelButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        titleInput = findViewById(R.id.titleInput)
        authorInput = findViewById(R.id.authorInput)
        ingrInput = findViewById(R.id.ingrInput)
        instInput = findViewById(R.id.instInput)

        submitButton = findViewById(R.id.submitBtn)
        cancelButton = findViewById(R.id.cancelBtn)

        submitButton.setOnClickListener { sendRecipeData() }
        cancelButton.setOnClickListener { cancelActivity() }
    }

    //Send new data to main activity
    private fun sendRecipeData() {
        if (titleInput.text.isNotEmpty() &&
            authorInput.text.isNotEmpty() &&
            ingrInput.text.isNotEmpty() &&
            instInput.text.isNotEmpty() ) {

            var title = titleInput.text.toString()
            var author = authorInput.text.toString()
            var ingredients = ingrInput.text.toString()
            var instructions = instInput.text.toString()

            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("title",title)
            intent.putExtra("author",author)
            intent.putExtra("ingredients",ingredients)
            intent.putExtra("instructions",instructions)
            startActivity(intent)

        }else{
            Toast.makeText(this,"All fields are required", Toast.LENGTH_LONG).show()
        }

    }

    //Cancel and back to main activity
    private fun cancelActivity(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}