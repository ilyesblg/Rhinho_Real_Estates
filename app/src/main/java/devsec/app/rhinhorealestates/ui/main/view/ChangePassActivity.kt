package devsec.app.rhinhorealestates.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import devsec.app.RhinhoRealEstates.R
import devsec.app.RhinhoRealEstates.databinding.ActivityCodeBinding
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.api.CodeRequest
import devsec.app.RhinhoRealEstates.databinding.ActivityChangePassBinding
import devsec.app.rhinhorealestates.data.api.changepass
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChangePassBinding

    lateinit var sessionPref: SessionPref
    lateinit var loadingDialog: LoadingDialog
    lateinit var sendcodebtn : Button
    lateinit var ePass: EditText
    lateinit var eVerif: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        sessionPref = SessionPref(this)

        ePass = findViewById(R.id.passwordchangeInputEditText)
        eVerif = findViewById(R.id.passwordchangeInputEditText2)
        sendcodebtn = findViewById(R.id.ChangePass)

        sendcodebtn.setOnClickListener {
            if (validatePass()) {
                validate()
            }
        }
    }

    private fun validatePass(): Boolean {
        val passText = ePass.text.toString().trim()
        val verifText = eVerif.text.toString().trim()
        if (passText.isEmpty()) {
            ePass.error = "Password is required"
            ePass.requestFocus()
            return false
        }
        if (passText != verifText){
            eVerif.error = "Password does not match"
            eVerif.requestFocus()
            return false
        }
        return true
    }

    private fun validate() {
        loadingDialog.startLoadingDialog()
        val email = intent.getStringExtra("email")
        val password = binding.passwordchangeInputEditText.text.toString()
        val Changepass = email?.let { changepass(it,password) }
        val apiService = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        if (Changepass != null) {
            apiService.changepass(Changepass).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    loadingDialog.dismissDialog()

                    if (response.isSuccessful) {
                        Toast.makeText(this@ChangePassActivity, "Password Changed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ChangePassActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                        Log.e("CodeActivity", "Error occurred: $errorMessage")

                        Toast.makeText(this@ChangePassActivity, "Verify Code", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    loadingDialog.dismissDialog()
                    Log.e("CodeActivity", "Network error occurred", t)

                    Toast.makeText(this@ChangePassActivity, "Verify Code", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}