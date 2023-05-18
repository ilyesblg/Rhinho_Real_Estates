package devsec.app.rhinhorealestates.ui.main.fragments;

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.ui.main.adapter.EstateAdapter
import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.view.EstateDescActivity
import devsec.app.rhinhorealestates.ui.main.view.MainMenuActivity
import devsec.app.rhinhorealestates.utils.services.Cart
import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarFragment : Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter : EstateAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Estate>
    private lateinit var searchView: SearchView
    private lateinit var noFoodLayout: LinearLayout
    lateinit var usernameString : String
    lateinit var sessionPref: SessionPref
    lateinit var user : HashMap<String, String>
    //    val loadingDialog = LoadingDialog(requireActivity())
    private lateinit var swiperRefreshLayout : SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rendez_vous_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        val navDrawerButton = view.findViewById<Button>(R.id.menu)


        val drawerLayout = (activity as MainMenuActivity).drawerLayout
        navDrawerButton.setOnClickListener {
            drawerLayout.open()
        }
        sessionPref = SessionPref(activity?.applicationContext!!)







    }


}












