package devsec.app.rhinhorealestates.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import devsec.app.RhinhoRealEstates.R
import devsec.app.rhinhorealestates.data.models.Estate

class EstateAdapter(private var estateList: List<Estate>): RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class EstateViewHolder(itemView: View,listener:OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val food_name = itemView.findViewById<TextView>(R.id.foodName)
        val food_image = itemView.findViewById<ImageView>(R.id.foodImage)
        val food_area = itemView.findViewById<TextView>(R.id.foodArea)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.estate_item, parent, false)
        return EstateViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = estateList[position]
        holder.food_name.text = estate.name
        holder.food_area.text = estate.area
        Picasso.get().load(estate.image).into(holder.food_image)
    }

    override fun getItemCount(): Int {
        return estateList.size
    }

    fun filterList(filteredList: ArrayList<Estate>) {
        estateList = filteredList
        notifyDataSetChanged()

    }
}
