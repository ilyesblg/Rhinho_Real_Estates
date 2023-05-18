package devsec.app.rhinhorealestates.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import devsec.app.RhinhoRealEstates.R
import devsec.app.RhinhoRealEstates.databinding.ActivityCodeBinding
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.api.CodeRequest
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CodeActivity : AppCompatActivity() {

    lateinit var sessionPref: SessionPref
    lateinit var loadingDialog: LoadingDialog
    lateinit var sendcodebtn : Button
    lateinit var eCode: EditText
    private lateinit var binding: ActivityCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        sessionPref = SessionPref(this)

        eCode = findViewById(R.id.CodeEditText)
        sendcodebtn = findViewById(R.id.CodeBtn)

        sendcodebtn.setOnClickListener {
            if (validateCode()) {
                validate()
            }
        }
    }

    private fun validateCode(): Boolean {
        val codeText = eCode.text.toString().trim()
        if (codeText.isEmpty()) {
            eCode.error = "Code is required"
            eCode.requestFocus()
            return false
        }
        return true
    }

    private fun validate() {
        loadingDialog.startLoadingDialog()
        val code = binding.CodeEditText.text.toString()
        val codeRequest = CodeRequest(code)
        val email = intent.getStringExtra("email")
        Log.d("CodeActivity", "Code: $code")
        val apiService = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        apiService.verifcode(codeRequest).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                loadingDialog.dismissDialog()

                if (response.isSuccessful) {
                    Toast.makeText(this@CodeActivity, "Password Changed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CodeActivity, ChangePassActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)

                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                    Log.e("CodeActivity", "Error occurred: $errorMessage")

                    Toast.makeText(this@CodeActivity, "Verify Code", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loadingDialog.dismissDialog()
                Log.e("CodeActivity", "Network error occurred", t)

                Toast.makeText(this@CodeActivity, "Verify Code", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
