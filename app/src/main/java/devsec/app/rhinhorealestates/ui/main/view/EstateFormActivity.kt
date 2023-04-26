package devsec.app.rhinhorealestates.ui.main.view



import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.RhinhoRealEstates.databinding.ActivityEstateFormBinding
import devsec.app.rhinhorealestates.data.models.Room
import devsec.app.rhinhorealestates.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class EstateFormActivity : AppCompatActivity() {

    lateinit var imgView: ImageView
    lateinit var addButton: AppCompatButton
    lateinit var submitButton: Button
//    lateinit var imageUri: Uri

    lateinit var titreInput: EditText
    lateinit var descInput: EditText
    lateinit var surfaceInput: EditText
    lateinit var numberRoomInput: EditText
    lateinit var bioCheckBox: CheckBox
    lateinit var categoryDropDown: AutoCompleteTextView
    lateinit var listDifficulty: ArrayList<String>
    lateinit var path: String
    private val IMAGE_PICK_CODE = 1
    private val REQUEST_CODE = 420

    private lateinit var binding: ActivityEstateFormBinding
    lateinit var roomsArray: ArrayList<String>
    lateinit var roomsTypeArray: ArrayList<String>

    lateinit var dialog: Dialog
    lateinit var nameInput: EditText
    lateinit var editTextRoom: EditText
    lateinit var listViewRoom: ListView
    lateinit var addRoom: AppCompatButton
    lateinit var removeRoom: AppCompatButton

    lateinit var layoutArray: ArrayList<ConstraintLayout>
    lateinit var linearlayoutRooms: LinearLayout

    //textView inputs
    lateinit var tv1: TextView
    lateinit var tv2: TextView
    lateinit var tv3: TextView
    lateinit var tv4: TextView
    lateinit var tv5: TextView
    lateinit var tv6: TextView
    lateinit var tv7: TextView
    lateinit var tv8: TextView
    lateinit var tv9: TextView
    lateinit var tv10: TextView
    lateinit var tv11: TextView
    lateinit var tv12: TextView
    lateinit var tv13: TextView
    lateinit var tv14: TextView
    lateinit var tv15: TextView
    lateinit var tv16: TextView
    lateinit var tv17: TextView
    lateinit var tv18: TextView
    lateinit var tv19: TextView
    lateinit var tv20: TextView

    //measure EditText
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var et3: EditText
    lateinit var et4: EditText
    lateinit var et5: EditText
    lateinit var et6: EditText
    lateinit var et7: EditText
    lateinit var et8: EditText
    lateinit var et9: EditText
    lateinit var et10: EditText
    lateinit var et11: EditText
    lateinit var et12: EditText
    lateinit var et13: EditText
    lateinit var et14: EditText
    lateinit var et15: EditText
    lateinit var et16: EditText
    lateinit var et17: EditText
    lateinit var et18: EditText
    lateinit var et19: EditText
    lateinit var et20: EditText

    //Type TextView
    lateinit var t1: TextView
    lateinit var t2: TextView
    lateinit var t3: TextView
    lateinit var t4: TextView
    lateinit var t5: TextView
    lateinit var t6: TextView
    lateinit var t7: TextView
    lateinit var t8: TextView
    lateinit var t9: TextView
    lateinit var t10: TextView
    lateinit var t11: TextView
    lateinit var t12: TextView
    lateinit var t13: TextView
    lateinit var t14: TextView
    lateinit var t15: TextView
    lateinit var t16: TextView
    lateinit var t17: TextView
    lateinit var t18: TextView
    lateinit var t19: TextView
    lateinit var t20: TextView

    lateinit var sessionPref: SessionPref
    lateinit var idUser: String
    lateinit var username: String

    lateinit var user: HashMap<String, String>
    var inc: Int = 0
    var checkRooms: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_form)
        sessionPref = SessionPref(this)
        user = sessionPref.getUserPref()
        idUser = user.get(SessionPref.USER_ID).toString()
        username = user.get(SessionPref.USER_NAME).toString()
        initRooms()
        roomsTypeArray = ArrayList()
        val sizeType: List<String> = Arrays.asList("Mg", "Gr", "Kg", "Ml", "Li", "Un")
        roomsTypeArray.addAll(sizeType)


        // Image Picker
        addButton = findViewById(R.id.add_button)
        imgView = findViewById(R.id.imageViewEstate)
        addButton.setOnClickListener {

            Log.d("TAG", "onClick: ")


            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, REQUEST_CODE)
            } else {
                pickImageFromGallery()
            }
        }




        categoryDropDown = findViewById(R.id.categoryDropDown)

        listDifficulty = ArrayList()
        listDifficulty.add("Easy")
        listDifficulty.add("Average")
        listDifficulty.add("Hard")

        val difficultyAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listDifficulty)

        categoryDropDown.setAdapter(difficultyAdapter)
        categoryDropDown.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG)
                .show()
        }
        setup()


    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//            imageUri = data?.data!!
//            imgView.setImageURI(imageUri)
//        }
//    }


    private fun setup() {
        //TextViews Rooms
        tv1 = findViewById(R.id.RoomTxt1)
        tv2 = findViewById(R.id.RoomTxt2)
        tv3 = findViewById(R.id.RoomTxt3)
        tv4 = findViewById(R.id.RoomTxt4)
        tv5 = findViewById(R.id.RoomTxt5)
        tv6 = findViewById(R.id.RoomTxt6)
        tv7 = findViewById(R.id.RoomTxt7)
        tv8 = findViewById(R.id.RoomTxt8)
        tv9 = findViewById(R.id.RoomTxt9)
        tv10 = findViewById(R.id.RoomTxt10)
        tv11 = findViewById(R.id.RoomTxt11)
        tv12 = findViewById(R.id.RoomTxt12)
        tv13 = findViewById(R.id.RoomTxt13)
        tv14 = findViewById(R.id.RoomTxt14)
        tv15 = findViewById(R.id.RoomTxt15)
        tv16 = findViewById(R.id.RoomTxt16)
        tv17 = findViewById(R.id.RoomTxt17)
        tv18 = findViewById(R.id.RoomTxt18)
        tv19 = findViewById(R.id.RoomTxt19)
        tv20 = findViewById(R.id.RoomTxt20)
        //EditText Sizes
        et1 = findViewById(R.id.SizeTxt1)
        et2 = findViewById(R.id.SizeTxt2)
        et3 = findViewById(R.id.SizeTxt3)
        et4 = findViewById(R.id.SizeTxt4)
        et5 = findViewById(R.id.SizeTxt5)
        et6 = findViewById(R.id.SizeTxt6)
        et7 = findViewById(R.id.SizeTxt7)
        et8 = findViewById(R.id.SizeTxt8)
        et9 = findViewById(R.id.SizeTxt9)
        et10 = findViewById(R.id.SizeTxt10)
        et11 = findViewById(R.id.SizeTxt11)
        et12 = findViewById(R.id.SizeTxt12)
        et13 = findViewById(R.id.SizeTxt13)
        et14 = findViewById(R.id.SizeTxt14)
        et15 = findViewById(R.id.SizeTxt15)
        et16 = findViewById(R.id.SizeTxt16)
        et17 = findViewById(R.id.SizeTxt17)
        et18 = findViewById(R.id.SizeTxt18)
        et19 = findViewById(R.id.SizeTxt19)
        et20 = findViewById(R.id.SizeTxt20)
        //TextView Sizes
        t1 = findViewById(R.id.SizeTxtType1)
        t2 = findViewById(R.id.SizeTxtType2)
        t3 = findViewById(R.id.SizeTxtType3)
        t4 = findViewById(R.id.SizeTxtType4)
        t5 = findViewById(R.id.SizeTxtType5)
        t6 = findViewById(R.id.SizeTxtType6)
        t7 = findViewById(R.id.SizeTxtType7)
        t8 = findViewById(R.id.SizeTxtType8)
        t9 = findViewById(R.id.SizeTxtType9)
        t10 = findViewById(R.id.SizeTxtType10)
        t11 = findViewById(R.id.SizeTxtType11)
        t12 = findViewById(R.id.SizeTxtType12)
        t13 = findViewById(R.id.SizeTxtType13)
        t14 = findViewById(R.id.SizeTxtType14)
        t15 = findViewById(R.id.SizeTxtType15)
        t16 = findViewById(R.id.SizeTxtType16)
        t17 = findViewById(R.id.SizeTxtType17)
        t18 = findViewById(R.id.SizeTxtType18)
        t19 = findViewById(R.id.SizeTxtType19)
        t20 = findViewById(R.id.SizeTxtType20)

        imgView = findViewById(R.id.imageViewEstate)
        addButton = findViewById(R.id.add_button)
        submitButton = findViewById(R.id.submit_button)
        titreInput = findViewById(R.id.titreEditText)
        descInput = findViewById(R.id.descEditText)
        surfaceInput = findViewById(R.id.surfaceEditText)
        numberRoomInput = findViewById(R.id.numberroomEditText)
        bioCheckBox = findViewById(R.id.bioCheckBox)
        categoryDropDown = findViewById(R.id.categoryDropDown)
        addRoom = findViewById(R.id.addRoom)
        removeRoom = findViewById(R.id.removeRoom)
        linearlayoutRooms = findViewById(R.id.roomInputs)

        removeRoom.visibility = View.GONE


        for (i in 1 until linearlayoutRooms.childCount - 1) {
            val view = linearlayoutRooms.getChildAt(i)
            view.visibility = View.GONE
        }

//addRoom Button
        addRoom.setOnClickListener {
            Log.d("+ INC", inc.toString())
            inc++
            linearlayoutRooms.getChildAt(inc).visibility = View.VISIBLE

            if (inc == 1) {
                removeRoom.visibility = View.VISIBLE
            }
            if (inc == 19) {
                addRoom.visibility = View.GONE
            }
            Log.d("++ INC", inc.toString())
        }

        //removeRoom Button
        removeRoom.setOnClickListener {
            Log.d("- INC", inc.toString())
            val ingredientInput = linearlayoutRooms.getChildAt(inc) as ConstraintLayout
            val ingredientText = ingredientInput.getChildAt(0) as TextView
            val ingredientSizeText = ingredientInput.getChildAt(2) as TextView
            val measureInput = ingredientInput.getChildAt(1) as TextInputLayout
            val measureEditText = measureInput.getChildAt(0) as FrameLayout
            val measureTxt = measureEditText.getChildAt(0) as EditText

            ingredientInput.visibility = View.GONE

            if (!ingredientText.text.isEmpty()) {
                roomsArray.add(ingredientText.text.toString())
                roomsArray.sorted()

                ingredientText.text = ""

            }
            ingredientSizeText.text = ""
            measureTxt.text.clear()

            inc--
            if (inc == 0) {
                removeRoom.visibility = View.GONE
            }
            if (inc == 18) {
                addRoom.visibility = View.VISIBLE
            }
            Log.d("-- INC", inc.toString())

        }

        for (i in 0 until linearlayoutRooms.childCount - 1) {
            val CL = linearlayoutRooms.getChildAt(i) as ConstraintLayout
            val TV = CL.getChildAt(0) as TextView
            TV.setOnClickListener {
                showDialogSpinner(TV, roomsArray, 0)
            }

        }
        for (i in 0 until linearlayoutRooms.childCount - 1) {
            val CL = linearlayoutRooms.getChildAt(i) as ConstraintLayout
            val TV = CL.getChildAt(2) as TextView
            TV.setOnClickListener {
                showDialogSpinner(TV, roomsTypeArray, 1)
            }

        }

        addButton.setOnClickListener {
            pickImageFromGallery()


        }

        submitButton.setOnClickListener {
            Log.d("checkSubmit", "0")
            var bio = false
            if (bioCheckBox.isChecked) {
                bio = true
            }
            Log.d("checkSubmit", "1")

            for (i in 0 until inc) {
                val ingredientInput = linearlayoutRooms.getChildAt(inc) as ConstraintLayout
                val ingredientText = ingredientInput.getChildAt(0) as TextView
                val measureInput = ingredientInput.getChildAt(1) as TextInputLayout
                val measureEditText = measureInput.getChildAt(0) as FrameLayout
                val measureTxt = measureEditText.getChildAt(0) as EditText
                val measureTypeTxt = ingredientInput.getChildAt(2) as TextView

                checkRooms = validateRooms(ingredientText, measureTxt, measureTypeTxt)
            }
            Log.d("checkSubmit", "2")

            if (validate(
                    titreInput,
                    descInput,
                    surfaceInput,
                    numberRoomInput,
                    categoryDropDown
                ) && checkRooms
            ) {
                Log.d("checkSubmit", "3")

                submit(
                    nameInput.text.toString(),
                    titreInput.text.toString(),
                    descInput.text.toString(),
                    surfaceInput.text.toString(),
                    numberRoomInput.text.toString(),
                    categoryDropDown.text.toString(),
                    bio,
                    idUser,
                    username,
                    tv1.text.toString(),
                    tv2.text.toString(),
                    tv3.text.toString(),
                    tv4.text.toString(),
                    tv5.text.toString(),
                    tv6.text.toString(),
                    tv7.text.toString(),
                    tv8.text.toString(),
                    tv9.text.toString(),
                    tv10.text.toString(),
                    tv11.text.toString(),
                    tv12.text.toString(),
                    tv13.text.toString(),
                    tv14.text.toString(),
                    tv15.text.toString(),
                    tv16.text.toString(),
                    tv17.text.toString(),
                    tv18.text.toString(),
                    tv19.text.toString(),
                    tv20.text.toString(),
                    measureFuse(et1, t1),
                    measureFuse(et2, t2),
                    measureFuse(et3, t3),
                    measureFuse(et4, t4),
                    measureFuse(et5, t5),
                    measureFuse(et6, t6),
                    measureFuse(et7, t7),
                    measureFuse(et8, t8),
                    measureFuse(et9, t9),
                    measureFuse(et10, t10),
                    measureFuse(et11, t11),
                    measureFuse(et12, t12),
                    measureFuse(et13, t13),
                    measureFuse(et14, t14),
                    measureFuse(et15, t15),
                    measureFuse(et16, t16),
                    measureFuse(et17, t17),
                    measureFuse(et18, t18),
                    measureFuse(et19, t19),
                    measureFuse(et20, t20)
                )

                Log.d("checkSubmit", "4")

//                retrofitUploadImage(imageUri)
            }


        }
//        val intent = Intent(this, FoodByRoomsActivity::class.java)
//        startActivity(intent)
    }

    private fun measureFuse(et: EditText, t: TextView): String {
        val measure=""
        if (et.text.isEmpty() || t.text.isEmpty() ){
            return measure
        }else{
            return et.text.toString() + " " + t.text.toString()
        }

    }

    private fun showDialogSpinner(tv:TextView,list:ArrayList<String>,iM:Int) {
        val dialog = Dialog(this@EstateFormActivity)
        dialog.setContentView(R.layout.dialog_searchable_spinner)
        dialog.window?.setLayout(1200, 1600)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        if (iM==0){
            dialog.findViewById<TextView>(R.id.spinnerHeader).text="Select an ingredient"
        }else if (iM==1){
            dialog.findViewById<TextView>(R.id.spinnerHeader).text="Select an Size Type"
        }

        editTextRoom = dialog.findViewById(R.id.searchRooms)
        listViewRoom = dialog.findViewById(R.id.listViewRooms)


        val adapter = ArrayAdapter(
            this@EstateFormActivity,
            android.R.layout.simple_list_item_1,
            list
        )
        listViewRoom.adapter = adapter

        editTextRoom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        if (iM==0){
            listViewRoom.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    if (tv.text.isEmpty()){
                        tv.text = (adapter.getItem(i) as String)
                        list.remove(adapter.getItem(i))
                        dialog.dismiss()
                    }else{
                        list.add(tv.text.toString())
                        tv.text = (adapter.getItem(i) as String)
                        list.remove(adapter.getItem(i))
                        dialog.dismiss()
                    }

                }
        }else if (iM==1){
            listViewRoom.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    tv.text = (adapter.getItem(i) as String)
                    dialog.dismiss()
                }
        }


    }
//    private fun retrofitUploadImage(imageUri: Uri) {
//        val file = File(imageUri.path)
//        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//        val body = MultipartBody.Part.createFormData("myFile", file.name, requestFile)
//        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
//        val call = retIn.uploadImage(body)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@RecetteFormActivity, "Image Uploaded", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    Toast.makeText(this@RecetteFormActivity, "Image Not Uploaded", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@RecetteFormActivity, "Image Not Uploaded", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//
//
//
//    }


    private fun checkDrop(a:String,b:ArrayList<String>):Boolean
    {
        var found = false
        for (n in b) {
            if (n == a) {
                found = true
                break
            }
        }
        return found

    }

    private fun validateRooms(ingredientTxt: TextView,measureTxt:EditText,ingredientSizeText:TextView ):Boolean{
        if(ingredientTxt.text.isEmpty() || measureTxt.text.isEmpty() || ingredientSizeText.text.isEmpty()){
            if (ingredientTxt.text.isEmpty()){
                ingredientTxt.error="ingredient is required"
                ingredientTxt.requestFocus()
            }
            if (measureTxt.text.isEmpty()){
                measureTxt.error="measure is required"
                measureTxt.requestFocus()
            }
            if (ingredientSizeText.text.isEmpty()){
                ingredientSizeText.error="measure Type is required"
                ingredientSizeText.requestFocus()
            }
         return false
        }
         return true

    }

    private fun validate(titreInput: EditText, descInput: EditText, dureeInput: EditText, personInput: EditText, difficulty: AutoCompleteTextView): Boolean {
        if (titreInput.text.isEmpty() || descInput.text.isEmpty() || dureeInput.text.isEmpty() || personInput.text.isEmpty() || !checkDrop(difficulty.text.toString(),listDifficulty)) {

            if (titreInput.text.isEmpty()) {
                titreInput.error = "title is required"
                titreInput.requestFocus()
            }

            if (descInput.text.isEmpty()) {
                descInput.error = "description is required"
                descInput.requestFocus()

            }


            if (dureeInput.text.isEmpty()) {
                dureeInput.error = "duration does not match"
                dureeInput.requestFocus()

            }

            if (personInput.text.isEmpty()) {
                personInput.error = "person is required"
                personInput.requestFocus()

            }

            if (!checkDrop(categoryDropDown.text.toString(),listDifficulty)) {
                difficulty.error = "difficulty is required"
                difficulty.requestFocus()

            }

            return false
        }



        return true
    }
    
    private fun submit(
        name:String,
        area: String,
        category: String,
        description: String,
        username: String,
        userId: String,
        isCoastal: Boolean,
        surface: String,
        strRoom1: String,
        strRoom2: String,
        strRoom3: String,
        strRoom4: String,
        strRoom5: String,
        strRoom6: String,
        strRoom7: String,
        strRoom8: String,
        strRoom9: String,
        strRoom10: String,
        strRoom11: String,
        strRoom12: String,
        strRoom13: String,
        strRoom14: String,
        strRoom15: String,
        strRoom16: String,
        strRoom17: String,
        strRoom18: String,
        strRoom19: String,
        strRoom20: String,
        strSize1: String,
        strSize2: String,
        strSize3: String,
        strSize4: String,
        strSize5: String,
        strSize6: String,
        strSize7: String,
        strSize8: String,
        strSize9: String,
        strSize10: String,
        strSize11: String,
        strSize12: String,
        strSize13: String,
        strSize14: String,
        strSize15: String,
        strSize16: String,
        strSize17: String,
        strSize18: String,
        strSize19: String,
        strSize20: String,
        measureFuse: String

    )
    {

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val image =  "test"+Random()
        val estateinfo = Estate (
            name,area,category, description, image, isCoastal,surface,username , userId, strRoom1,strRoom2,strRoom3,strRoom4,strRoom5,strRoom6,strRoom7,strRoom8,strRoom9,strRoom10,strRoom11,strRoom12,strRoom13,strRoom14,strRoom15,strRoom16,strRoom17,strRoom18,strRoom19,strRoom20,strSize1,strSize2,strSize3,strSize4,strSize5,strSize6,strSize7,strSize8,strSize9,strSize10,strSize11,strSize12,strSize13,strSize14,strSize15,strSize16,strSize17,strSize18,strSize19,strSize20

                )
        Log.d("check","4")
        retIn.addEstate(estateinfo).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    val intent = Intent(this@EstateFormActivity, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    val intent = Intent(this@EstateFormActivity, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@EstateFormActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun initRooms() {
        roomsArray = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getRoomsList()
        call.enqueue(object : Callback<List<Room>> {
            override fun onResponse(
                call: Call<List<Room>>,
                response: Response<List<Room>>
            ) {
                if (response.isSuccessful) {
                    val roomsList = response.body()
                    if (roomsList != null) {
                        for (ingredient in roomsList) {
                            roomsArray.add(ingredient.name)
                        }
                        Log.d("TAG", "onResponse: $roomsArray")
                    }
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}





//        ingredientInput1=findViewById(R.id.ingredientInput1)
//        ingredientInput2=findViewById(R.id.ingredientInput2)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput4=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)