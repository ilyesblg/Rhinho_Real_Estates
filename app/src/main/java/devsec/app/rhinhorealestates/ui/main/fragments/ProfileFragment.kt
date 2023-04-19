package devsec.app.rhinhorealestates.ui.main.fragments;

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import devsec.app.RhinhoRealEstates.R
import devsec.app.RhinhoRealEstates.databinding.FragmentProfileBinding
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.ui.main.view.EditProfileActivity
import devsec.app.rhinhorealestates.ui.main.view.LoginActivity
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    lateinit var binding : FragmentProfileBinding
    lateinit var session : SessionPref


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
        val adress = view.findViewById<TextView>(R.id.AdressProf)
        session.checkLogin()

        var user : HashMap<String, String> = session.getUserPref()
        username.text = user.get(SessionPref.USER_NAME)
        email.text = user.get(SessionPref.USER_EMAIL)
        phone.text = user.get(SessionPref.USER_PHONE)
        adress.text = user.get(SessionPref.USER_ADDRESS)

        val toolbar = view.findViewById<Toolbar>(R.id.profileToolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

    }
}