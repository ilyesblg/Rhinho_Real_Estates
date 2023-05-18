package devsec.app.rhinhorealestates.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout
import com.permissionx.guolindev.PermissionX
import devsec.app.RhinhoRealEstates.R
import devsec.app.RhinhoRealEstates.databinding.ActivityUploadImageBinding
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.ui.main.fragments.EstateFragment
import devsec.app.rhinhorealestates.ui.main.fragments.HomeFragment
import devsec.app.rhinhorealestates.ui.main.fragments.ProfileFragment
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UploadImageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityUploadImageBinding
    private lateinit var imageButton: ImageButton
    private lateinit var imageButton1: ImageButton
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton
    private lateinit var imageButton4: ImageButton
    lateinit var finishbutton: Button
    lateinit var addPicture: AppCompatButton
    lateinit var RemovePicture: AppCompatButton
    lateinit var uploadimages: Button

    var inc: Int = 0
    lateinit var linearlayoutImages: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addPicture = findViewById(R.id.addPicture)
        imageButton = findViewById(R.id.ImageButtonadd)
        imageButton1 = findViewById(R.id.ImageButtonadd1)
        imageButton2 = findViewById(R.id.ImageButtonadd2)
        imageButton3 = findViewById(R.id.ImageButtonadd3)
        imageButton4 = findViewById(R.id.ImageButtonadd4)
        RemovePicture = findViewById(R.id.removePicture)
        uploadimages = findViewById(R.id.uploadimages)
        RemovePicture.visibility = View.GONE
        linearlayoutImages = findViewById(R.id.pictureInputs)

        uploadimages.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            UploadImageActivity().finish()
        }
        val id = intent.getStringExtra("id")

        for (i in 1 until linearlayoutImages.childCount - 1) {
            val view = linearlayoutImages.getChildAt(i)
            view.visibility = View.GONE
        }
        addPicture.setOnClickListener {
            Log.d("+ INC", inc.toString())
            inc++
            linearlayoutImages.getChildAt(inc).visibility = View.VISIBLE

            if (inc == 1) {
                RemovePicture.visibility = View.VISIBLE
            }
            if (inc == 4) {
                addPicture.visibility = View.GONE
            }
            Log.d("++ INC", inc.toString())
        }

        RemovePicture.setOnClickListener {
            Log.d("- INC", inc.toString())
            val pictureinput = linearlayoutImages.getChildAt(inc) as ConstraintLayout


            pictureinput.visibility = View.GONE

            inc--
            if (inc == 0) {
                RemovePicture.visibility = View.GONE
                addPicture.visibility = View.VISIBLE
            }
            if (inc == 4) {
                RemovePicture.visibility = View.VISIBLE
            }
            Log.d("-- INC", inc.toString())
        }
        val startForImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(ContentValues.TAG, "testinggg testinggg: ${file!!.name}")
                    if (id != null) {
                        Log.d("this id : " , id)
                    }
                    multipartImageUpload(id,file)
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
        val startForImageResult1 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(ContentValues.TAG, "testinggg testinggg: ${file!!.name}")
                    if (id != null) {
                        Log.d("this id : " , id)
                    }
                    multipartImageUpload1(id,file)
                    imageButton1.setImageURI(uri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        imageButton1.setOnClickListener {

            PermissionX.init(this).permissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    ImagePicker.with(this).compress(1024).crop().createIntent {
                        startForImageResult1.launch(it)
                    }
                }
            }
        }
        val startForImageResult2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(ContentValues.TAG, "testinggg testinggg: ${file!!.name}")
                    if (id != null) {
                        Log.d("this id : " , id)
                    }
                    multipartImageUpload2(id,file)
                    imageButton2.setImageURI(uri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        imageButton2.setOnClickListener {

            PermissionX.init(this).permissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    ImagePicker.with(this).compress(1024).crop().createIntent {
                        startForImageResult2.launch(it)
                    }
                }
            }
        }
        val startForImageResult3 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(ContentValues.TAG, "testinggg testinggg: ${file!!.name}")
                    if (id != null) {
                        Log.d("this id : " , id)
                    }
                    multipartImageUpload3(id,file)
                    imageButton3.setImageURI(uri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        imageButton3.setOnClickListener {

            PermissionX.init(this).permissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    ImagePicker.with(this).compress(1024).crop().createIntent {
                        startForImageResult3.launch(it)
                    }
                }
            }
        }
        val startForImageResult4 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri = data?.data!!
                    val file = uri.path?.let { File(it) }
                    Log.d(ContentValues.TAG, "testinggg testinggg: ${file!!.name}")
                    if (id != null) {
                        Log.d("this id : " , id)
                    }
                    multipartImageUpload4(id,file)
                    imageButton4.setImageURI(uri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        imageButton4.setOnClickListener {

            PermissionX.init(this).permissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    ImagePicker.with(this).compress(1024).crop().createIntent {
                        startForImageResult4.launch(it)
                    }
                }
            }


        }
    }
    var textView: TextView? = null
    private fun multipartImageUpload(id: String?, file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImageEstate(id!!, it) }
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
    private fun multipartImageUpload1(id: String?, file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImageEstate1(id!!, it) }
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
    private fun multipartImageUpload2(id: String?, file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImageEstate2(id!!, it) }
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
    private fun multipartImageUpload3(id: String?, file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImageEstate3(id!!, it) }
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
    private fun multipartImageUpload4(id: String?, file: File?) {
        file?.name?.let { Log.e("Upload", it) }

        val mediaType = getMediaType(file?.extension)
        val requestBody = file?.asRequestBody(mediaType)
        val body: MultipartBody.Part? = requestBody?.let {
            MultipartBody.Part.createFormData("image", file.name, it)
        }

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = body?.let { retIn.uploadImageEstate4(id!!, it) }
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