package devsec.app.rhinhorealestates.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.adapter.ImagePagerAdapter
import devsec.app.rhinhorealestates.ui.main.adapter.IngredientsTextAdapter
import devsec.app.rhinhorealestates.ui.main.adapter.MeasuresTextAdapter
import devsec.app.rhinhorealestates.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context

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
    // Get the SharedPreferences instance
    private lateinit var sessionPref: SessionPref
    private lateinit var idd: String
    private lateinit var user: HashMap<String, String>

    // Get the user ID from preferences


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_desc)


        id = intent.getStringExtra("id").toString()
        val imageViewPager = findViewById<ViewPager>(R.id.imageViewPager)
        val recipeCategory = findViewById<TextView>(R.id.foodRecipeCategory)
        val recipeName = findViewById<TextView>(R.id.foodRecipeName)
        val recipeInstructions = findViewById<TextView>(R.id.foodRecipeInstructions)
        val likeButton = findViewById<AppCompatImageButton>(R.id.likeButton)
        val dislikeButton = findViewById<AppCompatImageButton>(R.id.dislikeButton)
        sessionPref = SessionPref(this)
        user = sessionPref.getUserPref()

        idd = user[SessionPref.USER_ID].toString()
        ingredientsList = ArrayList()
        mesuresList = ArrayList()



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
        findViewById<AppCompatImageButton>(R.id.chatButton)?.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("recipeInstructions", recipeInstructions.text.toString())
            intent.putExtra("user", user)
            startActivity(intent)

        }

        getRecipe(id, imageViewPager, recipeCategory, recipeName, recipeInstructions,idd,likeButton,dislikeButton, this)

        likeButton.setOnClickListener {
            val id = id
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            retIn.like(id, idd).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        if (likeButton.tag=="Pressed") {
                            likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                            likeButton.tag="notPressed"


                        } else {
                            likeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
                            likeButton.tag="Pressed"
                            dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                            dislikeButton.tag="notPressed"

                        }
                    } else {
                        // Handle the error response
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle the network error
                }
            })
        }




// Dislike button click listener
        dislikeButton.setOnClickListener {

            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            retIn.dislike(id,idd).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        if (dislikeButton.tag=="Pressed") {
                            dislikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                            dislikeButton.tag="notPressed"


                        } else {
                            dislikeButton.setImageResource(R.drawable.baseline_thumb_down_alt_24)
                            dislikeButton.tag="Pressed"
                            likeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                            likeButton.tag="notPressed"

                        }
                    } else {
                        // Handle the error response
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle the network error
                }
            })
        }



    }

// Like button click listener







    fun getRecipe(id: String, recipeImage: ViewPager, recipeCategory: TextView, recipeName: TextView, recipeInstructions: TextView, idd: String, LikeButton:AppCompatImageButton, disLikeButton:AppCompatImageButton,context: Context) {
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
                    recipeInstructions.text = food?.username
                    val BaseUrl = "http://192.168.1.111:9090/img/"
                    val imageUrls = arrayOf(BaseUrl+food?.image,
                        BaseUrl+food?.image1, BaseUrl+food?.image2,
                        BaseUrl+food?.image3,
                        BaseUrl+food?.image4).filterNotNull().toList() // Assuming 'images' is a list of image URLs in your 'Estate' class
                    Log.d("list : " , imageUrls.toString())
                    val adapter = ImagePagerAdapter(context, imageUrls)
                    recipeImage.adapter = adapter

                    var isLiked = food?.usersLiked?.toString()?.contains(idd)
                    if (isLiked!!) {
                        LikeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
                        LikeButton.tag="Pressed"
                        disLikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                        disLikeButton.tag="notPressed"
                    } else {
                        LikeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                        LikeButton.tag="notPressed"

                    }
                    var isdisLiked = food?.usersDisliked?.toString()?.contains(idd)
                    if (isdisLiked!!) {
                        disLikeButton.setImageResource(R.drawable.baseline_thumb_up_alt_24)
                        disLikeButton.tag="Pressed"
                        LikeButton.setImageResource(R.drawable.baseline_thumb_up_off_alt_24)
                        LikeButton.tag="notPressed"
                    } else {
                        disLikeButton.setImageResource(R.drawable.baseline_thumb_down_off_alt_24)
                        disLikeButton.tag="notPressed"

                    }
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

                    if (!food?.sizeFuse1.isNullOrEmpty() && food?.sizeFuse1.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse1.toString()) }
                    if (!food?.sizeFuse2.isNullOrEmpty() && food?.sizeFuse2.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse2.toString()) }
                    if (!food?.sizeFuse3.isNullOrEmpty() && food?.sizeFuse3.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse3.toString()) }
                    if (!food?.sizeFuse4.isNullOrEmpty() && food?.sizeFuse4.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse4.toString()) }
                    if (!food?.sizeFuse5.isNullOrEmpty() && food?.sizeFuse5.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse5.toString()) }
                    if (!food?.sizeFuse6.isNullOrEmpty() && food?.sizeFuse6.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse6.toString()) }
                    if (!food?.sizeFuse7.isNullOrEmpty() && food?.sizeFuse7.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse7.toString()) }
                    if (!food?.sizeFuse8.isNullOrEmpty() && food?.sizeFuse8.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse8.toString()) }
                    if (!food?.sizeFuse9.isNullOrEmpty() && food?.sizeFuse9.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse9.toString()) }
                    if (!food?.sizeFuse10.isNullOrEmpty() && food?.sizeFuse10.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse10.toString()) }
                    if (!food?.sizeFuse11.isNullOrEmpty() && food?.sizeFuse11.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse11.toString()) }
                    if (!food?.sizeFuse12.isNullOrEmpty() && food?.sizeFuse12.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse12.toString()) }
                    if (!food?.sizeFuse13.isNullOrEmpty() && food?.sizeFuse13.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse13.toString()) }
                    if (!food?.sizeFuse14.isNullOrEmpty() && food?.sizeFuse14.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse14.toString()) }
                    if (!food?.sizeFuse15.isNullOrEmpty() && food?.sizeFuse15.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse15.toString()) }
                    if (!food?.sizeFuse16.isNullOrEmpty() && food?.sizeFuse16.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse16.toString()) }
                    if (!food?.sizeFuse17.isNullOrEmpty() && food?.sizeFuse17.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse17.toString()) }
                    if (!food?.sizeFuse18.isNullOrEmpty() && food?.sizeFuse18.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse18.toString()) }
                    if (!food?.sizeFuse19.isNullOrEmpty() && food?.sizeFuse19.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse19.toString()) }
                    if (!food?.sizeFuse20.isNullOrEmpty() && food?.sizeFuse20.toString().trim().isNotBlank()) { measures.add(food?.sizeFuse20.toString()) }
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