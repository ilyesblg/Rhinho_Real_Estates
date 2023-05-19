package devsec.app.rhinhorealestates.ui.main.fragments;

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import devsec.app.RhinhoRealEstates.R
import devsec.app.RhinhoRealEstates.databinding.FragmentProfileBinding
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.api.ProfilePhotoResponse
import devsec.app.rhinhorealestates.data.models.User
import devsec.app.rhinhorealestates.ui.main.view.EditProfileActivity
import devsec.app.rhinhorealestates.ui.main.view.LoginActivity
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var session: SessionPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionPref(activity?.applicationContext!!)

        val loadingDialog = LoadingDialog(this.requireActivity())

        val username = view.findViewById<TextView>(R.id.profileUsernameINPT)
        val email = view.findViewById<TextView>(R.id.profileEmailINPT)
        val phone = view.findViewById<TextView>(R.id.profilePhoneINPT)
        val address = view.findViewById<TextView>(R.id.AddressProf)
        val image = view.findViewById<ImageView>(R.id.profileImage)
        session.checkLogin()

        val user: HashMap<String, String> = session.getUserPref()
        username.text = user[SessionPref.USER_NAME]
        email.text = user[SessionPref.USER_EMAIL]
        phone.text = user[SessionPref.USER_PHONE]
        address.text = user[SessionPref.USER_ADDRESS]

        // Fetch the profile photo URL from the server
        val userId = user.get(SessionPref.USER_ID).toString()

            // Make an API call to fetch the profile photo URL from the server using the user ID
            // Replace this with your actual API call implementation

            // Example code using Retrofit

            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            val call = retIn.getimage(userId)
            val baseUrl = "http://192.168.1.111:9090/img/"
            call.enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>, response: Response<User>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        val imageUrl = baseUrl+ user?.image
                        Picasso.get().load(imageUrl).into(image)
                    } else {

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                }


            })


        val toolbar = view.findViewById<Toolbar>(R.id.profileToolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }


}

