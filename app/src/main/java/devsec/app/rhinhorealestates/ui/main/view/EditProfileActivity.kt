package devsec.app.rhinhorealestates.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.permissionx.guolindev.PermissionX
import com.squareup.picasso.Picasso
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.User
import devsec.app.rhinhorealestates.utils.session.SessionPref
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import java.util.concurrent.TimeUnit


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
        val addressEditText = findViewById<EditText>(R.id.editProfileAddress)
        val phoneEditText = findViewById<EditText>(R.id.editProfilePhone)
        val updateButton = findViewById<Button>(R.id.updateProfileButton)

        usernameEditText.setText(user[SessionPref.USER_NAME])
        emailEditText.setText(user[SessionPref.USER_EMAIL])
        passwordEditText.setText(user[SessionPref.USER_PASSWORD])
        passwordEditText.visibility = View.INVISIBLE
        addressEditText.setText(user[SessionPref.USER_ADDRESS])
        phoneEditText.setText(user[SessionPref.USER_PHONE])
        imageButton = findViewById(R.id.editProfileImageButton)
        val imageUrl = "http://192.168.1.13:9090/img/"+user[SessionPref.USER_IMAGE] // Replace with the URL of the server image
        Picasso.get().load(imageUrl).into(imageButton)
        val startForImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(TAG, "testinggg testinggg: ${file!!.name}")
                    multipartImageUpload(file)
                    imageButton.setImageURI(uri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }

        imageButton.setOnClickListener {

                PermissionX.init(this).permissions(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ).request { allGranted, _, _ ->
                    if (allGranted) {
                        ImagePicker.with(this).compress(1024).crop().createIntent {
                            startForImageResult.launch(it)
                        }
                    }
                }


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
    var apiService: RestApiService? = null
    var picUri: Uri? = null
    private val IMAGE_RESULT = 200
    var fabCamera: FloatingActionButton? = null
    var fabUpload:FloatingActionButton? = null
    var mBitmap: Bitmap? = null
    var textView: TextView? = null


    private fun initRetrofitClient() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(RetrofitInstance.interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        apiService =
            Retrofit.Builder().baseUrl("http://192.168.1.13:9090").client(client).build().create(
                RestApiService::class.java
            )
    }


    fun getPickImageChooserIntent(): Intent? {
        val outputFileUri: Uri? = getCaptureImageOutputUri()
        val allIntents: MutableList<Intent> = ArrayList()
        val packageManager = packageManager
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }
        var mainIntent = allIntents[allIntents.size - 1]
        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }


    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = getExternalFilesDir("")
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_RESULT -> {
                    //val imageUri = getImageFilePath(data)
                    // Get the bitmap from the URI
                    val uri = data?.data!!
                    uri.path?.let {
                        var file = File (it)
                        multipartImageUpload(file)
                        Log.d("testing gg", "onActivityResult: ${file.name}")
                        imageButton.setImageURI(uri)
                    }

                    //mBitmap = BitmapFactory.decodeFile(imageUri)
                    //if (mBitmap != null) multipartImageUpload() else {
                       // Toast.makeText(applicationContext, "Bitmap is null. Try again", Toast.LENGTH_SHORT).show()
                  //  }
                }
            }
        }
    }


    private fun getImageFromFilePath(data: Intent?): String? {
        val isCamera = data == null || data.data == null
        return getPathFromURI(data!!.data)
    }

    fun getImageFilePath(data: Intent?): String? {
        return getImageFromFilePath(data)
    }

    private fun getPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentUri?.let { contentResolver.query(it, proj, null, null, null) }
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        if (column_index == null || cursor.count == 0) {
            cursor?.close()
            return null
        }
        cursor.moveToFirst()
        val path = cursor.getString(column_index)
        cursor.close()
        return path
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("pic_uri", picUri)
    }









    private fun multipartImageUpload(file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file?.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImage(id, it) }
        if (call != null) {
            call.enqueue(object : Callback<ResponseBody> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.e("Upload", response.body().toString())
                    if (response.code() == 200) {
                        textView?.text = "Uploaded Successfully!"
                    }
                    Log.d("Testin image test", response.body().toString())
                    Toast.makeText(
                        applicationContext,
                        response.code().toString() + " ",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "Request failed", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }
    }

    private fun getMediaType(extension: String?): MediaType? {
        return when (extension?.toLowerCase()) {
            "png" -> "image/png".toMediaTypeOrNull()
            "jpg", "jpeg" -> "image/jpeg".toMediaTypeOrNull()
            "gif" -> "image/gif".toMediaTypeOrNull()
            else -> null
        }
    }
    }



