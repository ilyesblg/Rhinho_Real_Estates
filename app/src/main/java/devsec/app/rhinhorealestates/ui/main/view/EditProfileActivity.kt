package devsec.app.rhinhorealestates.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.User
import devsec.app.rhinhorealestates.utils.session.SessionPref
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import okhttp3.*
import okhttp3.MediaType


class EditProfileActivity : AppCompatActivity() {

    private lateinit var sessionPref: SessionPref
    private lateinit var id: String
    private lateinit var image: String
    private lateinit var user: HashMap<String, String>
    private lateinit var imageButton: ImageButton

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        sessionPref = SessionPref(this)
        user = sessionPref.getUserPref()

        id = user[SessionPref.USER_ID].toString()
        image = user[SessionPref.USER_IMAGE].toString()

        val toolbar = findViewById<Toolbar>(R.id.editProfileToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { finish() }
        val usernameEditText = findViewById<EditText>(R.id.editProfileUsername)
        val emailEditText = findViewById<EditText>(R.id.editProfileEmail)
        val passwordEditText = findViewById<EditText>(R.id.editProfilePassword)
        val addressEditText = findViewById<EditText>(R.id.editProfileAdress)
        val phoneEditText = findViewById<EditText>(R.id.editProfilePhone)
        val updateButton = findViewById<Button>(R.id.updateProfileButton)

        usernameEditText.setText(user[SessionPref.USER_NAME])
        emailEditText.setText(user[SessionPref.USER_EMAIL])
        passwordEditText.setText(user[SessionPref.USER_PASSWORD])
        passwordEditText.visibility = View.INVISIBLE
        addressEditText.setText(user[SessionPref.USER_ADDRESS])
        phoneEditText.setText(user[SessionPref.USER_PHONE])

        imageButton = findViewById(R.id.editProfileImageButton)




        imageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, GALLERY_REQUEST_CODE)
        }

        updateButton.setOnClickListener {
            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)

            val userPref = User(
                id = "",
                username = usernameEditText.text.toString(),
                email = emailEditText.text.toString(),
                password = passwordEditText.text.toString(),
                address = addressEditText.text.toString(),
                phone = phoneEditText.text.toString()

            )

            val call = retIn.updateUser(id, userPref)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "Profile Updated",
                            Toast.LENGTH_SHORT
                        ).show()

                        sessionPref.setUserPref(
                            usernameEditText.text.toString(),
                            emailEditText.text.toString(),
                            passwordEditText.text.toString(),
                            addressEditText.text.toString(),
                            phoneEditText.text.toString()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            selectedImage?.let {
                val cursor = contentResolver.query(
                    it,
                    filePathColumn,
                    null,
                    null,
                    null
                )

                cursor?.let { c ->
                    c.moveToFirst()
                    val columnIndex = c.getColumnIndex(filePathColumn[0])
                    val imagePath = c.getString(columnIndex)

                    c.close()

                    val file = File(imagePath)

                    val requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                    val retIn =
                        RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
                    val call = retIn.uploadImage(id, body)

                    call.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    "Image uploaded",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(
                                    this@EditProfileActivity,
                                    EditProfileActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    "Failed to upload image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(
                                this@EditProfileActivity,
                                "Failed to upload image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }
}
