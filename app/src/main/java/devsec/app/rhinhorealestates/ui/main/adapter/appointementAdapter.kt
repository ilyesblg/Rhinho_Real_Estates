package devsec.app.rhinhorealestates.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.data.models.Appointement

class appointementAdapter (var appointements: List<Appointement>): RecyclerView.Adapter<appointementAdapter.AppointementViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rendez_vous_fragment, parent, false)
        return AppointementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointementViewHolder, position: Int) {
        val appointement= appointements[position]
        holder.bind(appointement)
    }

    override fun getItemCount() = appointements.size

    inner class AppointementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val nameAndLastName: TextView = itemView.findViewById(R.id.nameAndLastName)
        private val Date: TextView = itemView.findViewById(R.id.Date)








        fun bind(appointement: Appointement) {



            nameAndLastName.text = appointement.idUser
            Date.text= appointement.Date.toString();




        }
    }
}