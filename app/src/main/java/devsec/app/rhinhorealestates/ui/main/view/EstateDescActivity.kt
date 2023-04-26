package devsec.app.rhinhorealestates.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.adapter.IngredientsTextAdapter
import devsec.app.rhinhorealestates.ui.main.adapter.MeasuresTextAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstateDescActivity : AppCompatActivity() {
    lateinit var id : String
    lateinit var ingredientsList : ArrayList<String>
    lateinit var mesuresList : ArrayList<String>

    lateinit var ingredientsRecyclerView: RecyclerView
    lateinit var mesuresRecyclerView: RecyclerView

    lateinit var ingredientsAdapter: IngredientsTextAdapter
    lateinit var measuresAdapter: MeasuresTextAdapter

    lateinit var webView: WebView
    lateinit var recipeLink : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_desc)





        id = intent.getStringExtra("id").toString()
        val recipeImage = findViewById<ImageView>(R.id.recipeFoodImage)
        val recipeCategory = findViewById<TextView>(R.id.foodRecipeCategory)
        val recipeName = findViewById<TextView>(R.id.foodRecipeName)
        val recipeInstructions = findViewById<TextView>(R.id.foodRecipeInstructions)

        ingredientsList = ArrayList()
        mesuresList = ArrayList()

        getRecipe(id, recipeImage, recipeCategory, recipeName, recipeInstructions)



        val ingredientsLayoutManager = LinearLayoutManager(this)
        this.ingredientsRecyclerView = findViewById(R.id.ingredientListView)
        this.ingredientsRecyclerView.layoutManager = ingredientsLayoutManager

        val mesuresLayoutManager = LinearLayoutManager(this)
        this.mesuresRecyclerView = findViewById(R.id.mesureListView)
        this.mesuresRecyclerView.layoutManager = mesuresLayoutManager


        val toolbar = findViewById<Toolbar>(R.id.foodRecipeToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()

        }

        webView = findViewById(R.id.foodRecipeSourceWebView)
        val source = findViewById<TextView>(R.id.foodRecipeSource)
        source.visibility = TextView.GONE
        webView.settings.javaScriptEnabled = true
        webView.settings.safeBrowsingEnabled = true


        source.setOnClickListener(){
            Log.d("recipeLink", recipeLink)
            if (!recipeLink.isNullOrBlank()){
                webView.loadUrl(recipeLink)
            } else {
                Toast.makeText(this, "No source available", Toast.LENGTH_SHORT).show()
            }


        }
    }

    fun getRecipe(id: String, recipeImage: ImageView, recipeCategory: TextView, recipeName: TextView, recipeInstructions: TextView) {
        val ingredients = ArrayList<String>()
        val measures = ArrayList<String>()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getFoodById(id)
        call.enqueue(object : Callback<Estate> {
            override fun onResponse(call: Call<Estate>, response: Response<Estate>) {
                if (response.isSuccessful) {
                    val food = response.body()
                    recipeName.text = food?.name

                    recipeCategory.text = food?.category
                    recipeInstructions.text = food?.description
                    Picasso.get().load(food?.image).into(recipeImage)

                    if(!food?.strRoom1.isNullOrEmpty() && food?.strRoom1.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom1.toString()) }
                    if(!food?.strRoom2.isNullOrEmpty() && food?.strRoom2.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom2.toString()) }
                    if(!food?.strRoom3.isNullOrEmpty() && food?.strRoom3.toString().trim().isNotBlank()){ ingredients.add(food?.strRoom3.toString()) }
                    if(!food?.strRoom4.isNullOrEmpty() && food?.strRoom4.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom4.toString()) }
                    if(!food?.strRoom5.isNullOrEmpty() && food?.strRoom5.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom5.toString()) }
                    if(!food?.strRoom6.isNullOrEmpty() && food?.strRoom6.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom6.toString()) }
                    if(!food?.strRoom7.isNullOrEmpty() && food?.strRoom7.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom7.toString()) }
                    if(!food?.strRoom8.isNullOrEmpty() && food?.strRoom8.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom8.toString()) }
                    if(!food?.strRoom9.isNullOrEmpty() && food?.strRoom9.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom9.toString()) }
                    if(!food?.strRoom10.isNullOrEmpty() && food?.strRoom10.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom10.toString()) }
                    if(!food?.strRoom11.isNullOrEmpty() && food?.strRoom11.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom11.toString()) }
                    if(!food?.strRoom12.isNullOrEmpty() && food?.strRoom12.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom12.toString()) }
                    if(!food?.strRoom13.isNullOrEmpty() && food?.strRoom13.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom13.toString()) }
                    if(!food?.strRoom14.isNullOrEmpty() && food?.strRoom14.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom14.toString()) }
                    if(!food?.strRoom15.isNullOrEmpty() && food?.strRoom15.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom15.toString()) }
                    if(!food?.strRoom16.isNullOrEmpty() && food?.strRoom16.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom16.toString()) }
                    if(!food?.strRoom17.isNullOrEmpty() && food?.strRoom17.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom17.toString()) }
                    if(!food?.strRoom18.isNullOrEmpty() && food?.strRoom18.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom18.toString()) }
                    if(!food?.strRoom19.isNullOrEmpty() && food?.strRoom19.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom19.toString()) }
                    if(!food?.strRoom20.isNullOrEmpty() && food?.strRoom20.toString().trim().isNotBlank()) { ingredients.add(food?.strRoom20.toString()) }

                    ingredientsList.addAll(ingredients.filter { it.trim().isNotEmpty() })

                    ingredientsAdapter = IngredientsTextAdapter(ingredientsList)
                    ingredientsRecyclerView.adapter = ingredientsAdapter

                    if (!food?.strSize1.isNullOrEmpty() && food?.strSize1.toString().trim().isNotBlank()) { measures.add(food?.strSize1.toString()) }
                    if (!food?.strSize2.isNullOrEmpty() && food?.strSize2.toString().trim().isNotBlank()) { measures.add(food?.strSize2.toString()) }
                    if (!food?.strSize3.isNullOrEmpty() && food?.strSize3.toString().trim().isNotBlank()) { measures.add(food?.strSize3.toString()) }
                    if (!food?.strSize4.isNullOrEmpty() && food?.strSize4.toString().trim().isNotBlank()) { measures.add(food?.strSize4.toString()) }
                    if (!food?.strSize5.isNullOrEmpty() && food?.strSize5.toString().trim().isNotBlank()) { measures.add(food?.strSize5.toString()) }
                    if (!food?.strSize6.isNullOrEmpty() && food?.strSize6.toString().trim().isNotBlank()) { measures.add(food?.strSize6.toString()) }
                    if (!food?.strSize7.isNullOrEmpty() && food?.strSize7.toString().trim().isNotBlank()) { measures.add(food?.strSize7.toString()) }
                    if (!food?.strSize8.isNullOrEmpty() && food?.strSize8.toString().trim().isNotBlank()) { measures.add(food?.strSize8.toString()) }
                    if (!food?.strSize9.isNullOrEmpty() && food?.strSize9.toString().trim().isNotBlank()) { measures.add(food?.strSize9.toString()) }
                    if (!food?.strSize10.isNullOrEmpty() && food?.strSize10.toString().trim().isNotBlank()) { measures.add(food?.strSize10.toString()) }
                    if (!food?.strSize11.isNullOrEmpty() && food?.strSize11.toString().trim().isNotBlank()) { measures.add(food?.strSize11.toString()) }
                    if (!food?.strSize12.isNullOrEmpty() && food?.strSize12.toString().trim().isNotBlank()) { measures.add(food?.strSize12.toString()) }
                    if (!food?.strSize13.isNullOrEmpty() && food?.strSize13.toString().trim().isNotBlank()) { measures.add(food?.strSize13.toString()) }
                    if (!food?.strSize14.isNullOrEmpty() && food?.strSize14.toString().trim().isNotBlank()) { measures.add(food?.strSize14.toString()) }
                    if (!food?.strSize15.isNullOrEmpty() && food?.strSize15.toString().trim().isNotBlank()) { measures.add(food?.strSize15.toString()) }
                    if (!food?.strSize16.isNullOrEmpty() && food?.strSize16.toString().trim().isNotBlank()) { measures.add(food?.strSize16.toString()) }
                    if (!food?.strSize17.isNullOrEmpty() && food?.strSize17.toString().trim().isNotBlank()) { measures.add(food?.strSize17.toString()) }
                    if (!food?.strSize18.isNullOrEmpty() && food?.strSize18.toString().trim().isNotBlank()) { measures.add(food?.strSize18.toString()) }
                    if (!food?.strSize19.isNullOrEmpty() && food?.strSize19.toString().trim().isNotBlank()) { measures.add(food?.strSize19.toString()) }
                    if (!food?.strSize20.isNullOrEmpty() && food?.strSize20.toString().trim().isNotBlank()) { measures.add(food?.strSize20.toString()) }
                    mesuresList.addAll(measures.filter { it.trim().isNotEmpty() })

                    measuresAdapter = MeasuresTextAdapter(mesuresList)
                    mesuresRecyclerView.adapter = measuresAdapter
                    Log.d("mesureList", mesuresList.toString())
                    Log.d("mesures", measures.toString())
                }
            }

            override fun onFailure(call: Call<Estate>, t: Throwable) {
                Toast.makeText(this@EstateDescActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}