package com.example.recipeapp


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    var newTitle = ""
    var newAuthor = ""
    var newIngredients = ""
    var newInstructions = ""

    private lateinit var addRecipeBtn : Button

    var recipesData = ArrayList<Recipes>()
    private lateinit var myRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addRecipeBtn = findViewById(R.id.addRecipeBtn)

        myRV = findViewById(R.id.rvRecipes)

        // Call function to request data from API
        getData()

        //Send array to recycler view adapter
        myRV.adapter = RecyclerViewAdapter(recipesData)
        myRV.layoutManager = LinearLayoutManager(this)


        //OnClick listener to add new recipe
        addRecipeBtn.setOnClickListener { getNewRecipe() }

        // Post a new recipe data to API
        var tempNewTitle = intent.getStringExtra("title")
        var tempNewAuthor = intent.getStringExtra("author")
        var tempNewIngredients = intent.getStringExtra("ingredients")
        var tempNewInstructions = intent.getStringExtra("instructions")

        if (tempNewTitle != null && tempNewAuthor != null &&
            tempNewIngredients != null && tempNewInstructions != null ){

            newTitle = tempNewTitle.toString()
            newAuthor = tempNewAuthor.toString()
            newIngredients = tempNewIngredients.toString()
            newInstructions = tempNewInstructions.toString()

            postRecipe()
        }

    }


    // Get Recipes data from another page
    private fun getNewRecipe() {
        val intent = Intent(this, AddRecipeActivity::class.java)
        startActivity(intent)

    }


    //Request data from API
    private fun getData(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getRecipes()
            ?.enqueue(object : Callback<Recipes> {
                @RequiresApi(Build.VERSION_CODES.N)

                //onResponse function
                override fun onResponse(
                    call: Call<Recipes>,
                    response: Response<Recipes>
                ) {

                    if (response.body() != null) {

                        var body = response.body()

                        if (body != null)
                        {

                            for (i in 0 until body.size) {

                                recipesData.add(body)

                            }
                            Log.d("ArrayInfo", recipesData.toString())
                            myRV.adapter = RecyclerViewAdapter(recipesData)

                        }

                    }

                }

                //onFailure function
                override fun onFailure(call: Call<Recipes>, t: Throwable) {
                    Log.d("retrofit", "onFailure: ${t.message.toString()}")
                }

            })
    }


    // to store new recipe data
    private fun postRecipe(){

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.addNewRecipes(
            RecipesItem(
                0,
                newTitle,
                newAuthor,
                newIngredients,
                newInstructions
            )
        )
            ?.enqueue(object : Callback<Recipes> {
                @RequiresApi(Build.VERSION_CODES.N)

                //onResponse function
                override fun onResponse(
                    call: Call<Recipes>,
                    response: Response<Recipes>
                ) {

                    toastResponse()
                    myRV.adapter = RecyclerViewAdapter(recipesData)
                }

                //onFailure function
                override fun onFailure(call: Call<Recipes>, t: Throwable) {
                    Log.d("MAIN", "Something went wrong!")
                }

            })
    }


    //Msg function
    private fun toastResponse(){
        Toast.makeText(this,"The data has been added successfully", Toast.LENGTH_LONG).show()
    }

}


