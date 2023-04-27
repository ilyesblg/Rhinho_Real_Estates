package devsec.app.rhinhorealestates.ui.main.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.view.EstateFormActivity

import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.ui.main.view.EditProfileActivity
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.Menu

import android.net.Uri

import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity


import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.snackbar.Snackbar

import devsec.app.RhinhoRealEstates.databinding.ActivityMainMenuBinding
import devsec.app.rhinhorealestates.ui.main.fragments.HomeFragment
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.ui.main.fragments.BlogFragment
import devsec.app.rhinhorealestates.ui.main.fragments.EstateFragment
import devsec.app.rhinhorealestates.ui.main.fragments.ProfileFragment
import devsec.app.rhinhorealestates.utils.services.ConnectivityObserver
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.services.NetworkConnectivityObserver
import devsec.app.rhinhorealestates.utils.session.SessionPref
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogFragment : Fragment() {

    lateinit var formButton: FloatingActionButton
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        formButton= view.findViewById(R.id.formButton)
        formButton.setOnClickListener {
            val intent = Intent(context,EstateFormActivity::class.java)
            startActivity(intent)
        }


    }


}