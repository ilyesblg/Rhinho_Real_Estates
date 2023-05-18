package devsec.app.rhinhorealestates.ui.main.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
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
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.api.RestApiService
import devsec.app.rhinhorealestates.api.RetrofitInstance
import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.adapter.EstateAdapter
import devsec.app.rhinhorealestates.ui.main.view.EstateDescActivity

import devsec.app.rhinhorealestates.ui.main.view.MainMenuActivity
import devsec.app.rhinhorealestates.utils.services.Cart

import devsec.app.rhinhorealestates.utils.services.LoadingDialog
import devsec.app.rhinhorealestates.utils.session.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime

class HomeFragment : Fragment() {
    //************* Recommended food *************//

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter : EstateAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var foodArrayList: ArrayList<Estate>
    private lateinit var searchView: SearchView
    private lateinit var noFoodLayout: LinearLayout
    private lateinit var username: TextView
    lateinit var usernameString : String
    private lateinit var sessionPref: SessionPref
    private lateinit var idd: String
    private lateinit var user: HashMap<String, String>
    private lateinit var hello: TextView

    //    val loadingDialog = LoadingDialog(requireActivity())
    private lateinit var swiperRefreshLayout : SwipeRefreshLayout
    //***************** isBIO *******************//


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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
        getLikedList(idd)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.foodListView)
        recyclerView.layoutManager = layoutManager
        adapter = EstateAdapter(foodArrayList)
        recyclerView.adapter = adapter

        noFoodLayout.visibility = if (foodArrayList.isEmpty()) View.VISIBLE else View.GONE

        val hello = view.findViewById<TextView>(R.id.title)
        hello.text = "Those are Your Favourites, "

        swiperRefreshLayout.setOnRefreshListener {
            getLikedList(idd)
            this.swiperRefreshLayout.isRefreshing = false
        }
        //******************************** Init Bio Recette List **********************************************//
        val textView = view.findViewById<TextView>(R.id.title_username)
        textView.text = usernameString

        adapter.setOnItemClickListener(object : EstateAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, EstateDescActivity::class.java)
                intent.putExtra("id", foodArrayList[position].id)
                startActivity(intent)
            }
        })




    }


    @SuppressLint("NotifyDataSetChanged")
    private fun filterList(newText: String) {
        val filteredList = ArrayList<Estate>()
        for (item in foodArrayList) {
            if (item.name.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
            else if (item.area.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
            else if (item.category.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            AlertDialog.Builder(requireContext())
                .setTitle("No Result")
                .setMessage("No food found with the keyword $newText")
                .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        } else {
            adapter.filterList(filteredList)
            adapter.setOnItemClickListener(object : EstateAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(context, EstateDescActivity::class.java)
                    intent.putExtra("id", filteredList[position].id)
                    startActivity(intent)
                }
            })


        }

    }


    private fun getLikedList(id :String){
        loadingDialog.startLoadingDialog()
        foodArrayList = ArrayList()
        val ingredients = ArrayList<String>()
        foodArrayList.clear()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getLikedList(id)
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

