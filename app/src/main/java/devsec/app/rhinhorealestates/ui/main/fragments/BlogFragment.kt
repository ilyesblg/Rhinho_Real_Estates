package devsec.app.rhinhorealestates.ui.main.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.floatingactionbutton.FloatingActionButton


import devsec.app.rhinhorealestates.data.models.Estate
import devsec.app.rhinhorealestates.ui.main.view.EstateFormActivity

import devsec.app.RhinhoRealEstates.R



class BlogFragment : Fragment() {

    lateinit var formButton: FloatingActionButton


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
            val intent = Intent(context, EstateFormActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


    }


}