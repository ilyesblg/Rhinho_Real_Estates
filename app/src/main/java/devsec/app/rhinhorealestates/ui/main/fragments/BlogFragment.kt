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
import android.util.Log

import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.snackbar.Snackbar

import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.ui.main.adapter.EstateAdapter
import devsec.app.rhinhorealestates.ui.main.fragments.BlogFragment
import devsec.app.rhinhorealestates.ui.main.fragments.EstateFragment
import devsec.app.rhinhorealestates.ui.main.fragments.ProfileFragment
import devsec.app.rhinhorealestates.ui.main.view.EstateDescActivity
import devsec.app.rhinhorealestates.ui.main.view.MainMenuActivity
import devsec.app.rhinhorealestates.utils.services.Cart
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
    private lateinit var loadingDialog: LoadingDialog

    lateinit var formButton: FloatingActionButton
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var username: TextView
    private lateinit var hello: TextView
    lateinit var usernameString : String
    lateinit var sessionPref: SessionPref
    private lateinit var idd: String
    lateinit var user : HashMap<String, String>
    private lateinit var adapter : EstateAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Estate>
    private lateinit var searchView: SearchView
    private lateinit var noFoodLayout: LinearLayout

    //    val loadingDialog = LoadingDialog(requireActivity())
    private lateinit var swiperRefreshLayout : SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        val navDrawerButton = view.findViewById<Button>(R.id.menu)


        username = view.findViewById(R.id.title_username)

        sessionPref = SessionPref(activity?.applicationContext!!)
        user = sessionPref.getUserPref()
        usernameString = user.get(SessionPref.USER_NAME).toString()
        idd = user.get(SessionPref.USER_ID).toString()

        val drawerLayout = (activity as MainMenuActivity).drawerLayout
        navDrawerButton.setOnClickListener {
            drawerLayout.open()
        }

        swiperRefreshLayout = view.findViewById(R.id.foodIngredientSwipeRefresh)
        noFoodLayout = view.findViewById(R.id.noFoodLayout)
        getUserList(usernameString)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodListView)
        recyclerView.layoutManager = layoutManager
        adapter = EstateAdapter(foodArrayList)
        recyclerView.adapter = adapter

        noFoodLayout.visibility = if (foodArrayList.isEmpty()) View.VISIBLE else View.GONE


        swiperRefreshLayout.setOnRefreshListener {
            getUserList(usernameString)
            this.swiperRefreshLayout.isRefreshing = false
        }
        //******************************** Init Bio Recette List **********************************************//
        val textView = view.findViewById<TextView>(R.id.title_username)
        textView.text = usernameString
        val hello = view.findViewById<TextView>(R.id.title)
        hello.text = "Welcome to your Space, "

        adapter.setOnItemClickListener(object : EstateAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, EstateDescActivity::class.java)
                intent.putExtra("id", foodArrayList[position].id)
                startActivity(intent)
            }
        })


        username = view.findViewById(R.id.title_username)

        sessionPref = SessionPref(activity?.applicationContext!!)
        user = sessionPref.getUserPref()
        usernameString = user.get(SessionPref.USER_NAME).toString()



        formButton= view.findViewById(R.id.formButton)
        formButton.setOnClickListener {
            val intent = Intent(context,EstateFormActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getUserList(usernameString :String){
        loadingDialog.startLoadingDialog()
        foodArrayList = ArrayList()
        val ingredients = ArrayList<String>()
        foodArrayList.clear()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getUserList(usernameString)
        call.enqueue(object : Callback<List<Estate>> {
            override fun onResponse(call: Call<List<Estate>>, response: Response<List<Estate>>) {
                if (response.isSuccessful) {
                    foodArrayList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    loadingDialog.dismissDialog()
                    Log.d("FoodList", foodArrayList.toString())
                    Log.d("Cart", Cart.cart.toString())
                    Log.d("Ingredients", ingredients.toString())

                    if (foodArrayList.isEmpty()){
                        noFoodLayout.visibility = View.VISIBLE
                    } else {
                        noFoodLayout.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<List<Estate>>, t: Throwable) {
                Log.d("Error", t.message.toString())
                loadingDialog.dismissDialog()
                noFoodLayout.visibility = View.VISIBLE
            }
        })


    }
}