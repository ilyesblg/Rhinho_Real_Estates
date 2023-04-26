package devsec.app.rhinhorealestates.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.User
import devsec.app.rhinhorealestates.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    lateinit var sessionPref: SessionPref
    lateinit var id: String
    lateinit var image: String
    lateinit var user: HashMap<String, String>


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        sessionPref = SessionPref(this)

        val username = findViewById<TextView>(R.id.editProfileUsername)
        val email = findViewById<TextView>(R.id.editProfileEmail)
        val phone = findViewById<TextView>(R.id.editProfilePhone)
        val password = findViewById<TextView>(R.id.editProfilePassword)
        val adress = findViewById<TextView>(R.id.editProfileAdress)
        val updateButton = findViewById<Button>(R.id.updateProfileButton)


        user = sessionPref.getUserPref()
        image = user.get(SessionPref.USER_IMAGE).toString()
        id = user.get(SessionPref.USER_ID).toString()
        username.text = user.get(SessionPref.USER_NAME)
        email.text = user.get(SessionPref.USER_EMAIL)
        password.text = user.get(SessionPref.USER_PASSWORD)
        password.visibility = TextView.INVISIBLE
        adress.text = user.get(SessionPref.USER_ADDRESS)
        phone.text = user.get(SessionPref.USER_PHONE)

        val toolbar = findViewById<Toolbar>(R.id.editProfileToolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        updateButton.setOnClickListener {
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            val userPref = User(
                username = username.text.toString(),
                email = email.text.toString(),
                password = password.text.toString(),
                adress = adress.text.toString(),
                phone = phone.text.toString()
            )
            val call = retIn.updateUser(
                user.get(SessionPref.USER_ID).toString(),
                userPref
            )
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Profile Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                        sessionPref.setUserPref(
                            username.text.toString(),
                            email.text.toString(),
                            password.text.toString(),
                            adress.text.toString(),
                            phone.text.toString()
                        )
                        val intent = Intent(this@EditProfileActivity, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Failed to update profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Failed to update profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
        val imageView: ShapeableImageView = findViewById(R.id.editProfileImage)
        val url = "http://172.16.6.217:9090/img/"+image
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(imageView)

    }
}